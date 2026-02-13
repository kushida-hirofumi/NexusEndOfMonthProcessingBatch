package com.nexus.NexusEndOfMonthProcessingBatch.logger;

import com.nexus.NexusEndOfMonthProcessingBatch.utility.MyDateUtility;

import java.time.LocalDateTime;

public class BatchLogger {

    CustomLogger customLogger;

    //バッチタイトル
    String title;

    //バッチ開始時間
    LocalDateTime startLdt;

    //バッチ成功件数
    int successCounter;

    public BatchLogger(CustomLogger customLogger, String title, int max) {
        this.customLogger = customLogger;
        this.startLdt = MyDateUtility.nowLocalDateTime();
        this.title = title;
        this.successCounter = 0;
        this.customLogger.print(title + " 開始" +
                " [開始時刻:" + MyDateUtility.localDateTimeToString(startLdt, "yyyy/MM/dd  a  hh:mm") + "] [対応件数:" + max + "]");
    }

    public void print(String message) {
        customLogger.print(message);
    }

    public void addSuccessCounter() {
        successCounter++;
    }

    public void finish() {
        LocalDateTime endLdt = MyDateUtility.nowLocalDateTime();
        customLogger.print(title + " 終了" +
                " [終了時刻:" + MyDateUtility.localDateTimeToString(endLdt, "yyyy/MM/dd  a  hh:mm") + "] " +
                " [成功件数:" + successCounter + "] " +
                " [milliTime:" + (MyDateUtility.localDateTimeToMili(endLdt) - MyDateUtility.localDateTimeToMili(startLdt)) + "] ");
    }

}