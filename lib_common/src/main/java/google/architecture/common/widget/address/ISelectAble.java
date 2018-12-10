package google.architecture.common.widget.address;

/**
 * @author lq.zeng
 * @date 2018/5/29
 */

public interface ISelectAble {
    /**
     * 显示在栏目上的名字
     * */
    public String getName();

    /**
     * 用户设定的id，根据这个id，可以获取级栏目或者指定为最终栏目的id
     * */
    public int getId();

    /**
     * 自定义类型对象。
     * */
    public Object getArg();
}
