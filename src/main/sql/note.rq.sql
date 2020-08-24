--
-- SERVICE_ID = note.select
--

select * from T_NOTE



--
-- SERVICE_ID = note.insert
-- ROLES      = WRITER
--

insert into T_NOTE (NOTE_TID, INFO) values(:noteTid, :info)



--
-- SERVICE_ID = note.update
-- ROLES      = WRITER
--

update T_NOTE set INFO = :info where NOTE_TID = :noteTid



--
-- SERVICE_ID = note.delete
-- ROLES      = WRITER
--

delete from T_NOTE where NOTE_TID = :noteTid
