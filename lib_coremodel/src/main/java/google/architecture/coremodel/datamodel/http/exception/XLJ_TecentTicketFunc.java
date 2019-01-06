package google.architecture.coremodel.datamodel.http.exception;

import google.architecture.coremodel.data.xlj.TecentTicket;
import io.reactivex.functions.Function;

public class XLJ_TecentTicketFunc implements Function<TecentTicket,TecentTicket> {
    @Override
    public TecentTicket apply(TecentTicket tecentTicket) throws Exception {
        return tecentTicket;
    }
}
