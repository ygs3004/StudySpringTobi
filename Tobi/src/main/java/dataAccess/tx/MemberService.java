package dataAccess.tx;

import dataAccess.jpa.Member;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class MemberService {

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void add(Member add){

    }

    public void complexWork(){      
        this.add(new Member());
    }
}
