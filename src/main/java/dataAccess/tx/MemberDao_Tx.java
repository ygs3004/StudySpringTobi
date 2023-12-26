package dataAccess.tx;

import dataAccess.jpa.Member;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface MemberDao_Tx {

    public void add(Member member);
    public void add(List<Member> members);
    public void deleteAll();

    @Transactional(readOnly = true)
    public long count();
}
