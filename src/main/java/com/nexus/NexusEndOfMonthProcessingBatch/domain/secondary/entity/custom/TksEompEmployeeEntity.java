package com.nexus.NexusEndOfMonthProcessingBatch.domain.secondary.entity.custom;

import com.nexus.NexusEndOfMonthProcessingBatch.constant.EmployeeGenreEnum;
import lombok.Getter;
import lombok.Setter;

/**
 * 月末処理のレコードと紐づく社員のエンティティ
 */
@Getter
@Setter
public class TksEompEmployeeEntity {
    //社員分類
    EmployeeGenreEnum genre;
    //社員ID
    Integer employeeId;
    //検索ヒットしていればtrue
    boolean hit;

    public void setGenre(String genre) {
        this.genre = EmployeeGenreEnum.valueOfName(genre);
    }

    /**
     * 月末処理レコードを取得していい社員か確認
     * @param employeeGenreEnum 社員区分
     * @return  月末処理レコードを取得していい社員であればtrue
     */
    public boolean check(EmployeeGenreEnum employeeGenreEnum) {
        return genre.equals(employeeGenreEnum) && hit;
    }

}