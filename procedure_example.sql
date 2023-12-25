DROP FUNCTION IF EXISTS find_name;
create function find_name(in_id VARCHAR(255))
    returns varchar (255)
begin
    declare out_name varchar(255);
    select name
        into out_name
        from users
        where id = in_id;
    return out_name;
end;
