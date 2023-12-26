package dataAccess.tx;

import dataAccess.jpa.Member;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface MemberDao_TxTag {

    public void add(Member m);

    public void add(List<Member> members);

    public void deleteAll();

    @Transactional(readOnly = true)
    public Member get(int id);

    @Transactional(readOnly = true)
    public Member getAll();

    @Transactional(readOnly = true)
    public Member findMemberByName(String name);

}
