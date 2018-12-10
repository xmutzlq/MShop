package google.architecture.common.imgpicker.filter.callback;

import java.util.List;

import google.architecture.common.imgpicker.filter.entity.Directory;
import google.architecture.coremodel.data.BaseFile;

/**
 * @author lq.zeng
 * @date 2018/9/17
 */

public interface FilterResultCallback<T extends BaseFile> {
    void onResult(List<Directory<T>> directories);
}
