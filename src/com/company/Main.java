package com.company;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws ParseException {
//        cal();
//        local();

//        List<String> list = new LinkedList<>();
//        for (int i =0;i<100;i++){
//            list.add(String.valueOf((char)('A'+Math.random()*('Z'-'A'+1))));
//        }
//        System.out.println(list);
//        Map<String, Long> map = list.stream().
//                collect(Collectors.groupingBy(Function.identity(),Collectors.counting()));
//        System.out.println(map);
//
//        Map<String, Long> sortMap = new LinkedHashMap<>();
//        map.entrySet().stream().sorted(Map.Entry.comparingByKey()).
//                forEachOrdered(e -> sortMap.put(e.getKey(), e.getValue()));
//        System.out.println(sortMap);
//
//        List<String> keys = new LinkedList<>();
//        sortMap.entrySet().stream().forEachOrdered(e -> keys.add(e.getKey()));
//        System.out.println(keys);
//        System.out.println(sortMap.get(keys.get(0)));
//        String s = "dvfdd";
//        System.out.println(s instanceof String);
    }

    private static void local() {
        LocalDate today = LocalDate.now();
        System.out.println("2020-10-20".equals(today.toString()));
        String s = "20200701";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate yes = LocalDate.parse(s, DateTimeFormatter.BASIC_ISO_DATE);
        int year = yes.getYear();
        int month = yes.getMonthValue();
        int day = yes.getDayOfMonth();
        int dday = yes.getDayOfYear();
        System.out.println(yes +" -> " + year + " " + month + " "
        + day + " " + dday);
    }

    private static void cal() throws ParseException {
        System.out.println("Hello world !");
        int i = 1;

        SimpleDateFormat formats = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        Calendar c = Calendar.getInstance();
//        Integer year = Integer.valueOf(c.get(Calendar.YEAR));
        String s = "2020-09-28";
//        c.set(c.get(Calendar.YEAR),1, 0);
        SimpleDateFormat dte = new SimpleDateFormat("yyyy-MM-dd");
        Date d = dte.parse(s);

        Date date =  c.getTime();
        String yy = dte.format(c.getTime());
        System.out.println("nowday --> " + yy);

        if (date.before(d)){
            System.out.println(formats.format(d));
        }else {
            System.out.println(date.compareTo(d));
        }
    }
}
