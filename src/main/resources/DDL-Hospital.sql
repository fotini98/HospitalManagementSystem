-- Person(person_id, full_name, email, address, phone, gender, birth_date, age,password, created_by, last_updated, modified_by , deleted)
CREATE OR REPLACE FUNCTION update_modified_column() 
RETURNS TRIGGER AS $$
BEGIN
    NEW.last_updated = now();
    RETURN NEW; 
END;
$$ language 'plpgsql';

create trigger 
   last_updated 
before insert or update on 
  person 
for each row execute 
   function update_modified_column();

	CREATE OR REPLACE FUNCTION public.age_calc()
 RETURNS trigger
 LANGUAGE plpgsql
AS $function$
DECLARE
    age_int integer;
BEGIN

    SELECT INTO
        age_int
    date_part('years', age(NEW.birth_date))::int;

    NEW.age = age_int;
RETURN NEW;
END;

$function$

create trigger 
   age 
before insert or update on 
  person 
for each row execute 
   function age_calc();
   
CREATE OR REPLACE FUNCTION deleted_false() 
RETURNS TRIGGER AS $$
BEGIN
    NEW.deleted = 'false';
    RETURN NEW; 
END;
$$ language 'plpgsql';

create trigger 
   deleted 
before insert on 
  person 
for each row execute 
   function deleted_false();
   
   
   CREATE OR REPLACE FUNCTION update_end_time() 
RETURNS TRIGGER AS $$
BEGIN
    NEW.end_time = NEW.date + (20 * interval '1 minute');
    RETURN NEW; 
END;
$$ language 'plpgsql';

create trigger 
   end_time 
before insert or update on 
  appointment 
for each row execute 
   function update_end_time();


-- CREATE TYPE gender AS ENUM ('Female', 'Male');



CREATE TABLE person ( 
    person_id serial PRIMARY KEY,    
	full_name varchar(50),
	email varchar(20),
	address varchar(20),
    phone varchar(20),
     gender varchar,
	birth_date date,
	age int,
	password varchar(20),
	created_by varchar(20),
	last_updated TIMESTAMP,
	modified_by varchar(20),
	deleted boolean);
	
CREATE TRIGGER update_person_modtime BEFORE UPDATE ON person FOR EACH ROW EXECUTE PROCEDURE  update_modified_column();
-- Blood(blood_id, blood_group, amount)
-- CREATE TYPE blood_group AS ENUM ('A+', 'A-','B+', 'B-','AB++', 'AB-','0+', '0-');

CREATE TABLE blood ( 
    blood_id serial PRIMARY KEY,    
	blood_group varchar(20),
	amount int );
-- Patient(patient_id, person_id, blood_id)
CREATE TABLE patient ( 
    patient_id serial PRIMARY KEY,
	person_id int,
	blood_id int,
	CONSTRAINT person_id FOREIGN KEY (person_id) REFERENCES person(person_id),
    CONSTRAINT blood_id FOREIGN KEY (blood_id) REFERENCES blood(blood_id));


-- Role(role_id, role_name)(Doctor, Nurse, Receptionist, Admin, Patient)

CREATE TABLE role ( 
    role_id serial PRIMARY KEY,
	role_name varchar
	);

-- Department(department_id, name, description)
CREATE TABLE department ( 
    department_id serial PRIMARY KEY,    
	name varchar(20),
	description varchar(100) );
-- CREATE TYPE app_status AS ENUM ('Approved', 'Cancalled', 'Pending');	
-- Appointment(appointment_id, patient_id, doctor_id, date, status, created_by, last_updated, modified_by , deleted)

-- Employee(employee_id, person_id, role_id)
CREATE TABLE employee ( 
    employee_id serial PRIMARY KEY,
	person_id int,
	role_id int,
	department_id int,
	CONSTRAINT person_id FOREIGN KEY (person_id) REFERENCES person(person_id),
    CONSTRAINT role_id FOREIGN KEY (role_id) REFERENCES role(role_id),
	CONSTRAINT department_id FOREIGN KEY (department_id) REFERENCES department(department_id));







CREATE TABLE appointment ( 
    appointment_id serial PRIMARY KEY,
	patient_id int,
	doctor_id int,
	date date,
	status varchar(20),
	created_by varchar(20),
	last_updated TIMESTAMP,
	modified_by varchar(20),
	deleted boolean,
	CONSTRAINT patient_id FOREIGN KEY (patient_id) REFERENCES patient(patient_id),
    CONSTRAINT doctor_id FOREIGN KEY (doctor_id) REFERENCES employee(employee_id));


-- Prescription(prescription_id, appointment_id,  date, note)
CREATE TABLE prescription ( 
    prescription_id serial,
	appointment_id int unique,
    primary key(prescription_id),
   CONSTRAINT appointment_id FOREIGN KEY (appointment_id) REFERENCES appointment(appointment_id)
);

-- Medicine(medicine_id, medicine, prescription_id)
CREATE TABLE medicine ( 
    medicine_id serial PRIMARY KEY,  
	name varchar(30),
	strength int
	 );

CREATE TABLE prescription_medicine ( 
	prescription_id int,
    medicine_id int,
	dose int,
	duartion varchar(20),
	primary key(prescription_id,medicine_id),	
 	CONSTRAINT prescription_id FOREIGN KEY (prescription_id) REFERENCES prescription(prescription_id),
     CONSTRAINT medicine_id FOREIGN KEY (medicine_id) REFERENCES medicine(medicine_id));
     

-- Donner(donner_id, person_id, donnation_date)
	CREATE TABLE donner ( 
    donner_id serial PRIMARY KEY,    
	person_id int,
	donnation_date date,
    CONSTRAINT person_id FOREIGN KEY (person_id) REFERENCES person(person_id));
