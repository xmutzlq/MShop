package google.architecture.coremodel.data.xlj.searchfilter;

import com.google.gson.annotations.SerializedName;

public class FiltersNumber8Child {
    @SerializedName("1")
    private FiltersNumber number1;
    @SerializedName("2")
    private FiltersNumber number2;
    @SerializedName("5")
    private FiltersNumber number5;
    @SerializedName("10")
    private FiltersNumber number10;

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

    public FiltersNumber getNumber5() {
        return number5;
    }

    public void setNumber5(FiltersNumber number5) {
        this.number5 = number5;
    }

    public FiltersNumber getNumber10() {
        return number10;
    }

    public void setNumber10(FiltersNumber number10) {
        this.number10 = number10;
    }
}
