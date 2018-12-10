package google.architecture.common.viewmodel;

import google.architecture.coremodel.Account;
import google.architecture.coremodel.datamodel.http.EmptyConsumer;
import google.architecture.coremodel.datamodel.http.ErrorConsumer;
import google.architecture.coremodel.datamodel.http.repository.DeHongDataRepository;

/**
 * @author lq.zeng
 * @date 2018/9/10
 */

public class AddressViewModel extends UIViewModel {

    public void addressList() {
        if (isRunning.get()) return;
        disposable.add(DeHongDataRepository.get().addressList(Account.get().getUserId())
                .doOnSubscribe(disposable -> isRunning.set(true))
                .doOnTerminate(() -> isRunning.set(false))
                .doOnNext(result -> {
                    setDataObject(result.getData(), data);
                }).subscribe(new EmptyConsumer(), new ErrorConsumer()));
    }

    public void setDefaultAddress(String addressId, IMessageResult messageResult) {
        subscribe(DeHongDataRepository.get().saveAddress(Account.get().getUserId(), "set",
                "", "", "", "", "", "", "1", addressId,
                "", "", "", "", "", ""), messageResult);
    }

    public void addAddress(String provinceName, String cityName, String districtName, String townName, String address, String zipcode,
                           String consignee, String mobile, String provinceId, String cityId, String districtId, String townId, IMessageResult messageResult) {
        subscribe(DeHongDataRepository.get().saveAddress(Account.get().getUserId(), "add",
                provinceName, cityName, districtName, townName, address, zipcode, "-1", "",
                consignee, mobile, provinceId, cityId, districtId, townId), messageResult);
    }

    public void modifyAddress(String provinceName, String cityName, String districtName, String townName, String address, String zipcode, String isDefault,
                              String consignee, String mobile, String addressId, String provinceId, String cityId, String districtId, String townId, IMessageResult messageResult) {
        subscribe(DeHongDataRepository.get().saveAddress(Account.get().getUserId(), "edit",
                provinceName, cityName, districtName, townName, address, zipcode, isDefault, addressId,
                consignee, mobile, provinceId, cityId, districtId, townId), messageResult);
    }

    public void deleteAddress(String addressId, IMessageResult messageResult) {
        subscribe(DeHongDataRepository.get().saveAddress(Account.get().getUserId(), "del",
                "", "", "", "", "", "", "", addressId,
                "", "", "", "", "", ""), messageResult);
    }

    public void getProvinces(IDoOnNext doOnNext) {
        subscribe(DeHongDataRepository.get().getProvince(), doOnNext);
    }

    public void getCites(String provinceId, IDoOnNext doOnNext) {
        subscribe(DeHongDataRepository.get().getCity(provinceId), doOnNext);
    }

    public void getDistricts(String cityId, IDoOnNext doOnNext) {
        subscribe(DeHongDataRepository.get().getDistrict(cityId), doOnNext);
    }

    public void getTowns(String districtId, IDoOnNext doOnNext) {
        subscribe(DeHongDataRepository.get().getTown(districtId), doOnNext);
    }
}
