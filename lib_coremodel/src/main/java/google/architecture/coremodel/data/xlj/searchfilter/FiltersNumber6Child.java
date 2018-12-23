package google.architecture.coremodel.data.xlj.searchfilter;

import com.google.gson.annotations.SerializedName;

public class FiltersNumber6Child {
    @SerializedName("1")
    private FiltersNumber number1;
    @SerializedName("2")
    private FiltersNumber number2;

    public FiltersNumber getNumber1() {
        return number1;
    }

    public void setNumber1(FiltersNumber number1) {
        this.number1 = number1;
    }

    public FiltersNumber getNumber2() {
        return number2;
    }

    public void setNumber2(FiltersNumber number2) {
        this.number2 = number2;
    }
}
