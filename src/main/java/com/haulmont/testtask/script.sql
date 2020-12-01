create TABLE patients (
   id_patients BIGINT IDENTITY ,
   lastname VARCHAR(40) NOT NULL,
   firstname VARCHAR(40) NOT NULL,
   middlename VARCHAR(40) NOT NULL,
   phonenumber VARCHAR(30) NOT NULL,
   PRIMARY KEY (id_patients),
);

create TABLE doctors (
   id_doctors BIGINT IDENTITY,
   lastname VARCHAR(40) NOT NULL,
   firstname VARCHAR(40) NOT NULL,
   middlename VARCHAR(40) NOT NULL,
   specialization VARCHAR(30) NOT NULL,
   PRIMARY KEY (id_doctors),
);

create TABLE prescriptions (
    id_prescriptions BIGINT IDENTITY,
    description VARCHAR(1000) NOT NULL,
    id_patients BIGINT NOT NULL,
    id_doctors BIGINT NOT NULL,
    dateofcreation VARCHAR(10) NOT NULL,
    validityperiod VARCHAR(10) NOT NULL,
    priority VARCHAR(11) NOT NULL,
    PRIMARY KEY (id_prescriptions),
    FOREIGN KEY (id_patients) REFERENCES patients (id_patients),
    FOREIGN KEY (id_doctors) REFERENCES doctors (id_doctors),
);

insert into patients (lastname,firstname,middlename,phonenumber) values('Толкачев','Андрей','Владмирович','8 (927) 006-21-15');
insert into patients (lastname,firstname,middlename,phonenumber) values('Иванов','Иван','Иванович','8 (927) 235-67-68');
insert into patients (lastname,firstname,middlename,phonenumber) values('Петров','Петр','Петрович','8 (927) 245-89-19');
insert into patients (lastname,firstname,middlename,phonenumber) values('Васильев','Василий','Васильевич','8 (927) 226-23-67');
insert into patients (lastname,firstname,middlename,phonenumber) values('Игнатьев','Игнат','Игнатович','8 (927) 236-95-92');
insert into patients (lastname,firstname,middlename,phonenumber) values('Денисов','Денис','Денисович','8 (927) 986-75-35');
select  * from patients;

insert into doctors (lastname,firstname,middlename,specialization) values('Гаврилов','Гавриил','Гаврилович','Оториноларинголог');
insert into doctors (lastname,firstname,middlename,specialization) values('Дмитриев','Дмитрий','Дмитриевич','Психиатр');
insert into doctors (lastname,firstname,middlename,specialization) values('Данилов','Даниил','Данилович','Терапевт');
insert into doctors (lastname,firstname,middlename,specialization) values('Алексеев','Алексей','Алексеевич','Хирург');
insert into doctors (lastname,firstname,middlename,specialization) values('Викторов','Виктор','Викторович','Кардиолог');
insert into doctors (lastname,firstname,middlename,specialization) values('Григорьев','Григорий','Григорьевич','Дерматолог');
select  * from doctors;

insert into prescriptions (description,id_patients,id_doctors,dateofcreation,validityperiod,priority) values ('Назонекс - 50мг','0','0','01.01.2001','02.02.2002','Нормальный');
insert into prescriptions (description,id_patients,id_doctors,dateofcreation,validityperiod,priority) values ('Галоперидол - 5мг','1','1','03.03.2003','04.04.2004','Нормальный');
insert into prescriptions (description,id_patients,id_doctors,dateofcreation,validityperiod,priority) values ('Азитромицин - 500мг','2','2','05.05.2005','06.06.2006','Срочный');
insert into prescriptions (description,id_patients,id_doctors,dateofcreation,validityperiod,priority) values ('Кеторол - 10мг','3','3','07.07.2007','08.08.2008','Срочный');
insert into prescriptions (description,id_patients,id_doctors,dateofcreation,validityperiod,priority) values ('Амлодипин - 5мг','4','4','09.09.2009','10.10.2010','Немедленный');
insert into prescriptions (description,id_patients,id_doctors,dateofcreation,validityperiod,priority) values ('Дипроспан - 2мг+5мг/1мл','5','5','11.11.2011','12.12.2012','Немедленный');
select  * from prescriptions;

