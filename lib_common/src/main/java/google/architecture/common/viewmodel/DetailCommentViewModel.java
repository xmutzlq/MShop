package google.architecture.common.viewmodel;

import java.util.List;

import google.architecture.coremodel.Account;
import google.architecture.coremodel.datamodel.http.EmptyConsumer;
import google.architecture.coremodel.datamodel.http.ErrorConsumer;
import google.architecture.coremodel.datamodel.http.repository.DeHongDataRepository;

/**
 * @author lq.zeng
 * @date 2018/8/29
 */

public class DetailCommentViewModel extends UIViewModel {
    public void getDetailCommentInfo(String goodsId, String page, String pageSize) {
        if (isRunning.get()) return;
        disposable.add(DeHongDataRepository.get().getDetailComments(goodsId, page, pageSize)
                .doOnSubscribe(disposable -> isRunning.set(true))
                .doOnTerminate(() -> isRunning.set(false))
                .doOnNext(result -> {
                    checkEmpty(result.getData().getComment_list());
                    setDataObject(result.getData(), data);
                }).subscribe(new EmptyConsumer(), new ErrorConsumer()));
    }

    public void getCommentDetail(String orderId) {
        if (isRunning.get()) return;
        disposable.add(DeHongDataRepository.get().getCommentDetail(Account.get().getUserId(), orderId)
                .doOnSubscribe(disposable -> isRunning.set(true))
                .doOnTerminate(() -> isRunning.set(false))
                .doOnNext(result -> {
                    setDataObject(result.getData(), data);
                }).subscribe(new EmptyConsumer(), new ErrorConsumer()));
    }

    public void commitComment(String orderId, String is_anonymous, String commentJson, IMessageResult messageResult) {
        subscribe(DeHongDataRepository.get().commitComment(Account.get().getUserId(), orderId, is_anonymous, commentJson), messageResult);
    }

    public static class CommentJson {
        private String goods_id; //商品id
        private String item_ids; //规格ids
        private String content; //评价内容描述
        private List<String> image; //["图片1地址","图片2地址",... ...]
        private String satisfy_rank; //1好评 2中评 3差评
        private String deliver_rank; //物流服务质量1-5
        private String goods_rank; //商品描述相符1-5
        private String service_rank; //客服服务质量1-5

        public String getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(String goods_id) {
            this.goods_id = goods_id;
        }

        public String getItem_ids() {
            return item_ids;
        }

        public void setItem_ids(String item_ids) {
            this.item_ids = item_ids;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public List<String> getImage() {
            return image;
        }

        public void setImage(List<String> image) {
            this.image = image;
        }

        public String getSatisfy_rank() {
            return satisfy_rank;
        }

        public void setSatisfy_rank(String satisfy_rank) {
            this.satisfy_rank = satisfy_rank;
        }

        public String getDeliver_rank() {
            return deliver_rank;
        }

        public void setDeliver_rank(String deliver_rank) {
            this.deliver_rank = deliver_rank;
        }

        public String getGoods_rank() {
            return goods_rank;
        }

        public void setGoods_rank(String goods_rank) {
            this.goods_rank = goods_rank;
        }

        public String getService_rank() {
            return service_rank;
        }

        public void setService_rank(String service_rank) {
            this.service_rank = service_rank;
        }
    }
}
