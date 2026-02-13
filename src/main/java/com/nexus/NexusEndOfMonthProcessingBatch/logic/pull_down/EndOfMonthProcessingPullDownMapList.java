package com.nexus.NexusEndOfMonthProcessingBatch.logic.pull_down;

import lombok.Getter;

import java.util.ArrayList;

@Getter
public class EndOfMonthProcessingPullDownMapList {
    ArrayList<EndOfMonthProcessingPullDownDataPrimitive> list = new ArrayList<>();

    public void add(EndOfMonthProcessingPullDownDataPrimitive node) {
        list.add(node);
    }

    public EndOfMonthProcessingPullDownDataPrimitive searchByRecordId(int recordId) {
        for(EndOfMonthProcessingPullDownDataPrimitive node : list) {
            if(node.getRecordId()==recordId) return node;
        }
        return null;
    }
}