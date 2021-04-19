package cn.delei.java.feature;

import cn.delei.util.PrintUtil;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * LocalDate 日期类 Demo
 *
 * @author deleiguo
 * @since 1.8
 */
public class LocalDateDemo {
    public static void main(String[] args) {

        // ==> Clock
        PrintUtil.printDivider("Clock");
        Clock clock = Clock.systemUTC();
        System.out.println(clock.millis());
        clock = Clock.systemDefaultZone();
        System.out.println("ZoneId=" + clock.getZone());
        clock = Clock.system(ZoneId.of("Europe/Paris")); // 巴黎时区
        System.out.println(clock.millis()); // 每次调用将返回当前瞬时时间(UTC)
        clock = Clock.system(ZoneId.of("Asia/Shanghai"));// 上海时区
        System.out.println(clock.millis());// 每次调用将返回当前瞬时时间(UTC)

        // ==> Instant时间戳
        PrintUtil.printDivider("Instant 时间戳");
        Instant instant = Instant.now();
        System.out.println("toEpochMilli=" + instant.toEpochMilli()); //精确到毫秒
        System.out.println("getNano=" + instant.getNano());
        System.out.println("getEpochSecond=" + instant.getEpochSecond());  //精确到秒 得到相对于1970-01-01 00:00:00 UTC的一个时间
        System.out.println("toString=" + instant.toString());

        // ==> 格式化
        PrintUtil.printDivider("格式化");
        LocalDate localDate = LocalDate.now();
        System.out.println(String.format("date now : %s", localDate));
        LocalDateTime localDateTime = LocalDateTime.now().withNano(0);
        System.out.println(String.format("time now : %s", localDateTime));
        localDateTime = LocalDateTime.now(ZoneId.of("Europe/Paris"));
        System.out.println(String.format("Europe/Paris now : %s", localDateTime));
        // 必须指定时区
        ZonedDateTime zonedDateTime = ZonedDateTime.of(LocalDateTime.now(), ZoneId.systemDefault());
        System.out.println(format(localDateTime));
        System.out.println(format(zonedDateTime));
        System.out.println(format(LocalDateTime.now()));

        // ==> 字符串转日期
        PrintUtil.printDivider("字符串转日期");
        localDate = LocalDate.of(2019, 12, 20);
        System.out.println(" LocalDate.of: " + format(localDate));
        localDate = LocalDate.parse("2021-04-19");
        System.out.println(" LocalDate.parse: " + format(localDate));
        localDateTime = LocalDateTime.of(2019, 12, 20, 23, 10, 59);
        System.out.println(" LocalDateTime.of: " + format(localDateTime));
        localDateTime = LocalDateTime.parse("2021-04-19T15:54:54");
        System.out.println(" LocalDateTime.parse: " + format(localDateTime));

        // ==> 日期获取
        PrintUtil.printDivider("日期获取");
        localDate = LocalDate.of(2019, 12, 20);
        localDateTime = LocalDateTime.of(2019, 12, 20, 23, 10, 59, 889);
        System.out.println("日期 " + format(localDateTime));
        System.out.println("年份：" + localDateTime.getYear());
        System.out.println("月份：" + localDateTime.getMonth());
        System.out.println("当年中的第多少天：" + localDateTime.getDayOfYear());
        System.out.println("当月的第多少天：" + localDateTime.getDayOfMonth());
        System.out.println("星期：" + localDateTime.getDayOfWeek());
        System.out.println("时：" + localDateTime.getHour());
        System.out.println("分：" + localDateTime.getMinute());
        System.out.println("秒：" + localDateTime.getSecond());
        System.out.println("毫秒：" + localDateTime.getNano());
        System.out.println("是否闰年：" + localDate.isLeapYear());

        // ==> 日期计算
        PrintUtil.printDivider("日期计算");
        LocalDate aferCalc;
        localDate = LocalDate.now();
        System.out.println("日期 " + format(localDate));
        aferCalc = localDate.plusWeeks(1L);
        System.out.println("一周后:" + format(aferCalc));
        aferCalc = localDate.plusDays(20L);
        System.out.println("20后:" + format(aferCalc));
        aferCalc = localDate.minusDays(20L);
        System.out.println("20天前:" + format(aferCalc));
        aferCalc = localDate.plus(1, ChronoUnit.WEEKS);
        System.out.println("2周后 plus ChronoUnit:" + format(aferCalc));

        PrintUtil.printDivider("日期相隔");
        LocalDate date01 = LocalDate.of(2008, 12, 25);
        LocalDate date02 = LocalDate.of(2019, 3, 11);
        System.out.println("日期01: " + format(date01));
        System.out.println("日期02: " + format(date02));
        Period period = Period.between(date01, date02);
        // 这里period.getDays()得到的天是抛去年月以外的天数，并不是总天数
        System.out.println("date01 到 date02 相隔："
                + period.getYears() + "年"
                + period.getMonths() + "月"
                + period.getDays() + "天");
        long day = date02.toEpochDay() - date01.toEpochDay();
        System.out.println("date01 到 date02 相隔：" + day + "天");

        PrintUtil.printDivider("时间相隔");
        LocalDateTime time01 = LocalDateTime.of(2018, 12, 25, 8, 15, 46);
        LocalDateTime time02 = LocalDateTime.of(2019, 3, 11, 13, 10, 35);
        //表示两个瞬时时间的时间段
        System.out.println("时间01: " + format(time01));
        System.out.println("时间02: " + format(time02));
        Duration d1 = Duration.between(time01.toInstant(ZoneOffset.UTC), time02.toInstant(ZoneOffset.UTC));
        //得到相应的时差
        System.out.println(d1.toDays());
        System.out.println(d1.toHours());
        System.out.println(d1.toMinutes());
        System.out.println(d1.toMillis());
        System.out.println(d1.toNanos());
    }

    static String format(LocalDate date) {
        final DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return format.format(date);
    }

    static String format(LocalDateTime time) {
        final DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return format.format(time);
    }

    static String format(ZonedDateTime zonedDateTime) {
        final DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSSZ");
        return format.format(zonedDateTime);
    }
}
