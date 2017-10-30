package com.datarepublic.simplecab;

import com.datarepublic.simplecab.configuration.MainConfiguration;
import com.datarepublic.simplecab.services.SimpleCabService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;

import static com.datarepublic.simplecab.configuration.MainConfiguration.SIMPLE_SERVICE_BEAN_NAME;

@Getter
public class Client {

    private static final ApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfiguration.class);

    private SimpleCabService simpleCabService;

    public Client() {
        simpleCabService = applicationContext.getBean(SIMPLE_SERVICE_BEAN_NAME, SimpleCabService.class);
    }

    public static void main(String[] arg) {
        Client client = new Client();
        SimpleCabService simpleCabService = client.getSimpleCabService();
        print();

        while (true) {
            Scanner s = new Scanner(System.in);
            String input = s.nextLine();
            String[] command = input.split(" ");
            if (command.length == 0) {
                print();
            } else if (command.length == 1 && "3".equals(command[0])) {
                System.out.println(simpleCabService.deleteCache() ? "Deleted Cache" : "Cache cannot be deleted");
                System.out.println();
                System.out.println();
            } else if (command.length >= 3) {
                String dateStr = command[1];
                try {
                    Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
                    ObjectMapper objectMapper = new ObjectMapper();

                    if ("1".equals(command[0])) {
                        String[] medallionArr = Arrays.copyOfRange(command, 2, command.length);
                        System.out.println(objectMapper.writeValueAsString(simpleCabService.getMedallionsSummary(date, medallionArr)));
                        System.out.println();
                        System.out.println();
                        print();
                    } else if ("2".equals(command[0])) {
                        String[] medallionArr = Arrays.copyOfRange(command, 2, command.length);
                        System.out.println(objectMapper.writeValueAsString(simpleCabService.getMedallionsSummary(date, true, medallionArr)));
                        System.out.println();
                        System.out.println();
                        print();
                    } else {
                        print();
                    }
                } catch (ParseException e) {
                    System.out.println("Invalid date format, Expected yyyy-MM-dd");
                    print();
                } catch (JsonProcessingException e) {
                    System.out.println("Output from server cannot be parsed");
                    e.printStackTrace();
                    print();
                }
            } else {
                print();
            }
        }
    }

    private static void print() {
        System.out.println();
        System.out.println();
        System.out.println("Expecting one of the following operations!!");
        System.out.println("To Get Medallion count pass arguments as 1 <yyyy-MM-dd> <medallion-name1> <medallion-name2> ...");
        System.out.println("To Get Medallion count with cache refresh pass arguments as 2 <yyyy-MM-dd> <medallion-name1> <medallion-name2> ...");
        System.out.println("To delete all cache pass arguments as 3");
        System.out.println();
        System.out.println();
    }

}
