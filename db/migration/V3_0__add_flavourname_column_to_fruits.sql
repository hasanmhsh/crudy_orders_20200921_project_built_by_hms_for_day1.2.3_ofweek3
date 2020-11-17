ALTER TABLE fruits ADD flavourname varchar;
update fruits set flavourname='undefined' where true;
ALTER TABLE fruits ALTER COLUMN flavourname set  not null ;