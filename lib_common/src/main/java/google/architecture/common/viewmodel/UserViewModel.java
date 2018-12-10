package google.architecture.common.viewmodel;

import google.architecture.coremodel.Account;
import google.architecture.coremodel.datamodel.http.repository.DeHongDataRepository;

/**
 * @author lq.zeng
 * @date 2018/10/24
 */

public class UserViewModel extends UIViewModel {
    public void commitFeedBack(String content, String images, IMessageResult messageResult) {
        subscribe(DeHongDataRepository.get().commitFeedBack(Account.get().getUserId(), content, images), messageResult);
    }
}
