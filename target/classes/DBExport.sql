--
-- PostgreSQL database dump
--

-- Dumped from database version 13.2
-- Dumped by pg_dump version 13.1

-- Started on 2021-05-04 00:01:53

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 227 (class 1255 OID 18183)
-- Name: age_calc(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.age_calc() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
DECLARE
    age_int integer;
BEGIN

    SELECT INTO
        age_int
    date_part('years', age(NEW.birth_date))::int;

    NEW.age = age_int;
RETURN NEW;
END;

$$;


ALTER FUNCTION public.age_calc() OWNER TO postgres;

--
-- TOC entry 226 (class 1255 OID 19304)
-- Name: age_in_years(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.age_in_years() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
  NEW.age := date_part('year', CURRENT_TIME - NEW.birth_date::timestamp);
  RETURN NEW; -- or the insert or update would fail
END; 
$$;


ALTER FUNCTION public.age_in_years() OWNER TO postgres;

--
-- TOC entry 225 (class 1255 OID 18190)
-- Name: deleted_false(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.deleted_false() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
    NEW.deleted = 'false';
    RETURN NEW; 
END;
$$;


ALTER FUNCTION public.deleted_false() OWNER TO postgres;

--
-- TOC entry 224 (class 1255 OID 18185)
-- Name: delted_false(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.delted_false() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
DECLARE
    deleted boolean;
BEGIN

    SELECT INTO
        deleted

    NEW.deleted = 'false';
RETURN NEW;
END;

$$;


ALTER FUNCTION public.delted_false() OWNER TO postgres;

--
-- TOC entry 229 (class 1255 OID 19861)
-- Name: update_end_time(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.update_end_time() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
    NEW.end_time = appointment.date + (20 * interval '1 minute');
    RETURN NEW; 
END;
$$;


ALTER FUNCTION public.update_end_time() OWNER TO postgres;

--
-- TOC entry 228 (class 1255 OID 19859)
-- Name: update_modified_clumn(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.update_modified_clumn() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
    NEW.last_updated = now();
    RETURN NEW; 
END;
$$;


ALTER FUNCTION public.update_modified_clumn() OWNER TO postgres;

--
-- TOC entry 223 (class 1255 OID 17683)
-- Name: update_modified_column(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.update_modified_column() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
    NEW.last_updated = now();
    RETURN NEW; 
END;
$$;


ALTER FUNCTION public.update_modified_column() OWNER TO postgres;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 213 (class 1259 OID 17768)
-- Name: appointment; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.appointment (
    appointment_id integer NOT NULL,
    patient_id integer,
    doctor_id integer,
    date timestamp without time zone,
    status character varying(20),
    created_by character varying(20),
    last_updated timestamp without time zone,
    modified_by character varying(20),
    deleted boolean,
    end_time timestamp without time zone
);


ALTER TABLE public.appointment OWNER TO postgres;

--
-- TOC entry 212 (class 1259 OID 17766)
-- Name: appointment_appointment_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.appointment_appointment_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.appointment_appointment_id_seq OWNER TO postgres;

--
-- TOC entry 3136 (class 0 OID 0)
-- Dependencies: 212
-- Name: appointment_appointment_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.appointment_appointment_id_seq OWNED BY public.appointment.appointment_id;


--
-- TOC entry 222 (class 1259 OID 19855)
-- Name: appointment_end; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW public.appointment_end AS
 SELECT appointment.date AS appointment_start,
    (appointment.date + ((20)::double precision * '00:01:00'::interval)) AS appointment_end
   FROM public.appointment
  WHERE ((appointment.status)::text = 'Approved'::text)
  ORDER BY appointment.appointment_id;


ALTER TABLE public.appointment_end OWNER TO postgres;

--
-- TOC entry 203 (class 1259 OID 17700)
-- Name: blood; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.blood (
    blood_id integer NOT NULL,
    blood_group character varying(20),
    amount integer
);


ALTER TABLE public.blood OWNER TO postgres;

--
-- TOC entry 202 (class 1259 OID 17698)
-- Name: blood_blood_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.blood_blood_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.blood_blood_id_seq OWNER TO postgres;

--
-- TOC entry 3137 (class 0 OID 0)
-- Dependencies: 202
-- Name: blood_blood_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.blood_blood_id_seq OWNED BY public.blood.blood_id;


--
-- TOC entry 209 (class 1259 OID 17737)
-- Name: department; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.department (
    department_id integer NOT NULL,
    name character varying(20),
    description character varying(300)
);


ALTER TABLE public.department OWNER TO postgres;

--
-- TOC entry 208 (class 1259 OID 17735)
-- Name: department_department_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.department_department_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.department_department_id_seq OWNER TO postgres;

--
-- TOC entry 3138 (class 0 OID 0)
-- Dependencies: 208
-- Name: department_department_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.department_department_id_seq OWNED BY public.department.department_id;


--
-- TOC entry 217 (class 1259 OID 17822)
-- Name: donner; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.donner (
    donner_id integer NOT NULL,
    person_id integer,
    donnation_date date
);


ALTER TABLE public.donner OWNER TO postgres;

--
-- TOC entry 216 (class 1259 OID 17820)
-- Name: donner_donner_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.donner_donner_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.donner_donner_id_seq OWNER TO postgres;

--
-- TOC entry 3139 (class 0 OID 0)
-- Dependencies: 216
-- Name: donner_donner_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.donner_donner_id_seq OWNED BY public.donner.donner_id;


--
-- TOC entry 211 (class 1259 OID 17745)
-- Name: employee; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.employee (
    employee_id integer NOT NULL,
    person_id integer,
    role_id integer,
    department_id integer
);


ALTER TABLE public.employee OWNER TO postgres;

--
-- TOC entry 210 (class 1259 OID 17743)
-- Name: employee_employee_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.employee_employee_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.employee_employee_id_seq OWNER TO postgres;

--
-- TOC entry 3140 (class 0 OID 0)
-- Dependencies: 210
-- Name: employee_employee_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.employee_employee_id_seq OWNED BY public.employee.employee_id;


--
-- TOC entry 215 (class 1259 OID 17799)
-- Name: medicine; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.medicine (
    medicine_id integer NOT NULL,
    name character varying(30),
    strength integer
);


ALTER TABLE public.medicine OWNER TO postgres;

--
-- TOC entry 214 (class 1259 OID 17797)
-- Name: medicine_medicine_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.medicine_medicine_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.medicine_medicine_id_seq OWNER TO postgres;

--
-- TOC entry 3141 (class 0 OID 0)
-- Dependencies: 214
-- Name: medicine_medicine_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.medicine_medicine_id_seq OWNED BY public.medicine.medicine_id;


--
-- TOC entry 205 (class 1259 OID 17708)
-- Name: patient; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.patient (
    patient_id integer NOT NULL,
    person_id integer,
    blood_id integer
);


ALTER TABLE public.patient OWNER TO postgres;

--
-- TOC entry 204 (class 1259 OID 17706)
-- Name: patient_patient_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.patient_patient_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.patient_patient_id_seq OWNER TO postgres;

--
-- TOC entry 3142 (class 0 OID 0)
-- Dependencies: 204
-- Name: patient_patient_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.patient_patient_id_seq OWNED BY public.patient.patient_id;


--
-- TOC entry 201 (class 1259 OID 17686)
-- Name: person; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.person (
    person_id integer NOT NULL,
    full_name character varying(50),
    email character varying(20),
    address character varying(20),
    phone character varying(20),
    gender character varying,
    birth_date date,
    age integer,
    password character varying(20),
    created_by character varying(20),
    last_updated timestamp without time zone,
    modified_by character varying(20),
    deleted boolean
);


ALTER TABLE public.person OWNER TO postgres;

--
-- TOC entry 200 (class 1259 OID 17684)
-- Name: person_person_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.person_person_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.person_person_id_seq OWNER TO postgres;

--
-- TOC entry 3143 (class 0 OID 0)
-- Dependencies: 200
-- Name: person_person_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.person_person_id_seq OWNED BY public.person.person_id;


--
-- TOC entry 219 (class 1259 OID 19200)
-- Name: prescription; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.prescription (
    prescription_id integer NOT NULL,
    appointment_id integer
);


ALTER TABLE public.prescription OWNER TO postgres;

--
-- TOC entry 220 (class 1259 OID 19213)
-- Name: prescription_medicine; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.prescription_medicine (
    prescription_id integer NOT NULL,
    medicine_id integer NOT NULL,
    prescription_medicine_id integer NOT NULL,
    duration character varying,
    dose character varying
);


ALTER TABLE public.prescription_medicine OWNER TO postgres;

--
-- TOC entry 221 (class 1259 OID 19830)
-- Name: prescription_medicine_prescription_medicine_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.prescription_medicine_prescription_medicine_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.prescription_medicine_prescription_medicine_id_seq OWNER TO postgres;

--
-- TOC entry 3144 (class 0 OID 0)
-- Dependencies: 221
-- Name: prescription_medicine_prescription_medicine_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.prescription_medicine_prescription_medicine_id_seq OWNED BY public.prescription_medicine.prescription_medicine_id;


--
-- TOC entry 218 (class 1259 OID 19198)
-- Name: prescription_prescription_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.prescription_prescription_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.prescription_prescription_id_seq OWNER TO postgres;

--
-- TOC entry 3145 (class 0 OID 0)
-- Dependencies: 218
-- Name: prescription_prescription_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.prescription_prescription_id_seq OWNED BY public.prescription.prescription_id;


--
-- TOC entry 207 (class 1259 OID 17726)
-- Name: role; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.role (
    role_id integer NOT NULL,
    role_name character varying
);


ALTER TABLE public.role OWNER TO postgres;

--
-- TOC entry 206 (class 1259 OID 17724)
-- Name: role_role_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.role_role_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.role_role_id_seq OWNER TO postgres;

--
-- TOC entry 3146 (class 0 OID 0)
-- Dependencies: 206
-- Name: role_role_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.role_role_id_seq OWNED BY public.role.role_id;


--
-- TOC entry 2930 (class 2604 OID 17771)
-- Name: appointment appointment_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.appointment ALTER COLUMN appointment_id SET DEFAULT nextval('public.appointment_appointment_id_seq'::regclass);


--
-- TOC entry 2925 (class 2604 OID 17703)
-- Name: blood blood_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.blood ALTER COLUMN blood_id SET DEFAULT nextval('public.blood_blood_id_seq'::regclass);


--
-- TOC entry 2928 (class 2604 OID 17740)
-- Name: department department_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.department ALTER COLUMN department_id SET DEFAULT nextval('public.department_department_id_seq'::regclass);


--
-- TOC entry 2932 (class 2604 OID 17825)
-- Name: donner donner_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.donner ALTER COLUMN donner_id SET DEFAULT nextval('public.donner_donner_id_seq'::regclass);


--
-- TOC entry 2929 (class 2604 OID 17748)
-- Name: employee employee_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.employee ALTER COLUMN employee_id SET DEFAULT nextval('public.employee_employee_id_seq'::regclass);


--
-- TOC entry 2931 (class 2604 OID 17802)
-- Name: medicine medicine_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.medicine ALTER COLUMN medicine_id SET DEFAULT nextval('public.medicine_medicine_id_seq'::regclass);


--
-- TOC entry 2926 (class 2604 OID 17711)
-- Name: patient patient_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.patient ALTER COLUMN patient_id SET DEFAULT nextval('public.patient_patient_id_seq'::regclass);


--
-- TOC entry 2924 (class 2604 OID 17689)
-- Name: person person_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.person ALTER COLUMN person_id SET DEFAULT nextval('public.person_person_id_seq'::regclass);


--
-- TOC entry 2933 (class 2604 OID 19203)
-- Name: prescription prescription_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.prescription ALTER COLUMN prescription_id SET DEFAULT nextval('public.prescription_prescription_id_seq'::regclass);


--
-- TOC entry 2934 (class 2604 OID 19832)
-- Name: prescription_medicine prescription_medicine_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.prescription_medicine ALTER COLUMN prescription_medicine_id SET DEFAULT nextval('public.prescription_medicine_prescription_medicine_id_seq'::regclass);


--
-- TOC entry 2927 (class 2604 OID 17729)
-- Name: role role_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.role ALTER COLUMN role_id SET DEFAULT nextval('public.role_role_id_seq'::regclass);


--
-- TOC entry 3122 (class 0 OID 17768)
-- Dependencies: 213
-- Data for Name: appointment; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.appointment (appointment_id, patient_id, doctor_id, date, status, created_by, last_updated, modified_by, deleted, end_time) FROM stdin;
6	1	5	2021-04-26 18:21:00	Completed	test	\N	test	f	\N
7	1	5	2021-04-26 20:38:00	Completed	test	\N	test	f	\N
17	1	5	2021-05-04 10:20:00	Completed	test	\N	test	f	\N
3	1	5	2021-04-25 21:57:00	Completed	test	\N	test	t	\N
19	1	5	2021-05-11 21:57:00	Approved	test	\N	test	f	\N
4	1	5	2021-05-04 09:00:00	Approved	test	\N	test	t	2021-05-04 09:20:00
13	1	5	2021-05-04 09:20:00	Approved	test	\N	test	t	2021-05-04 09:40:00
14	1	5	2021-05-04 09:40:00	Approved	test	\N	test	t	2021-05-04 10:00:00
18	1	5	2021-05-04 10:40:00	Approved	test	\N	test	f	2021-05-04 10:40:00
25	1	5	2021-05-03 23:53:00	Approved	test	2021-05-03 22:53:44.4556	test	f	\N
26	1	5	2021-05-04 09:40:00	Approved	test	2021-05-03 22:54:24.960118	test	f	\N
15	1	5	2021-05-04 10:00:00	Approved	test	2021-05-03 23:10:26.967717	test	t	2021-05-04 10:20:00
27	1	5	2021-05-04 13:25:00	Approved	test	2021-05-03 23:12:33.074088	test	f	\N
8	1	5	2021-04-26 20:38:00	Requested	test	\N	test	t	\N
11	1	5	2021-04-26 19:31:00	Rejected	test	\N	test	t	\N
9	1	5	2021-04-26 14:38:00	Completed	test	\N	test	f	\N
10	1	5	2021-04-26 21:33:00	Completed	test	\N	test	f	\N
5	1	5	2021-04-20 02:57:00	Completed	test	\N	test	t	\N
12	1	5	2021-04-27 23:53:00	Requested	test	\N	test	f	\N
16	1	5	\N	Requested	test	\N	test	f	\N
\.


--
-- TOC entry 3112 (class 0 OID 17700)
-- Dependencies: 203
-- Data for Name: blood; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.blood (blood_id, blood_group, amount) FROM stdin;
1	A+	3
2	A-	1
3	B+	2
4	AB+	4
5	AB-	8
6	0+	2
7	0-	4
\.


--
-- TOC entry 3118 (class 0 OID 17737)
-- Dependencies: 209
-- Data for Name: department; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.department (department_id, name, description) FROM stdin;
1	Infectious Disease	The Department of Infectious Diseases at SIMS Hospital cares for children, adolescents and adult with a variety of infections. The infectious disease practices at SIMS Hospital are designed to meet the needs of patients in the hospital and in the outpatient setting.
2	Cardiology	Lorem ipsum\n
3	Dermatology	Lorem ipsum
\.


--
-- TOC entry 3126 (class 0 OID 17822)
-- Dependencies: 217
-- Data for Name: donner; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.donner (donner_id, person_id, donnation_date) FROM stdin;
\.


--
-- TOC entry 3120 (class 0 OID 17745)
-- Dependencies: 211
-- Data for Name: employee; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.employee (employee_id, person_id, role_id, department_id) FROM stdin;
6	19	2	2
5	18	2	2
8	21	1	3
9	22	2	2
10	23	2	2
11	24	2	2
12	25	2	1
7	20	2	2
13	26	3	1
15	28	2	1
14	27	5	1
17	31	5	3
18	32	3	1
19	33	5	1
21	35	5	2
20	34	5	1
22	36	3	1
23	37	2	1
16	29	2	1
24	38	2	1
\.


--
-- TOC entry 3124 (class 0 OID 17799)
-- Dependencies: 215
-- Data for Name: medicine; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.medicine (medicine_id, name, strength) FROM stdin;
1	Paracetamol	500
2	Aspirine	500
\.


--
-- TOC entry 3114 (class 0 OID 17708)
-- Dependencies: 205
-- Data for Name: patient; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.patient (patient_id, person_id, blood_id) FROM stdin;
1	1	1
\.


--
-- TOC entry 3110 (class 0 OID 17686)
-- Dependencies: 201
-- Data for Name: person; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.person (person_id, full_name, email, address, phone, gender, birth_date, age, password, created_by, last_updated, modified_by, deleted) FROM stdin;
18	Pellumb Piper Update	pellumb@gmail.com	Mine Peza	069741478	FEMALE	1964-06-04	56	1234	test	2021-04-29 16:53:26.775411	Administrator	t
21	Administrator	admin@gmail.com	bhvcx	47412025	MALE	1996-04-26	25	1234	Administrator	2021-04-29 16:53:26.775411	Administrator	f
22	Najada Como	najada@gmail.com	test	0697548111	\N	1967-04-04	54	\N	Administrator	2021-04-29 16:53:26.775411	Administrator	\N
23	Najada Como	najada@gmail.com	test	0697548111	\N	1967-04-04	54	\N	Administrator	2021-04-29 16:53:26.775411	Administrator	\N
24	Fotini Prillo	fotini@gmail.com	5 maj	0692958743	\N	1998-04-24	23	\N	Administrator	2021-04-29 16:53:26.775411	Administrator	\N
25	Fotini Prillo	fotini@gmail.com	5 maj	0692958743	\N	2021-04-04	0	\N	Administrator	2021-04-29 16:53:26.775411	Administrator	t
20	testupdate	test@gmail.com	tet	4105620541	\N	2001-04-03	20	\N	Administrator	2021-04-29 16:53:26.775411	Administrator	f
26	Fotini Prilloupdate	fotini@gmail.com	5 maj	0692958743	\N	2021-04-05	0	\N	Administrator	2021-04-29 16:53:26.775411	Administrator	t
38	Fotini Prillo	fotini@gmail.com	5 maj	0692958743	\N	1954-05-24	66	\N	Administrator	2021-05-01 19:32:11.066665	\N	t
30	test	test	test	54321543e	male	2001-01-01	20	pass	Administrator	2021-04-29 16:53:26.775411	Administrator	f
29	Fotini Prillooo	fotini@gmail.com	5 maj	0692958743	\N	2021-04-04	0	\N	Administrator	2021-05-01 19:32:12.941421	Administrator	t
1	patient name test	patient@gmail.com	test	741012487	FEMALE\n	2001-04-03	20	test	Administrator	2021-04-29 16:53:57.3758	\N	\N
19	Najada Como	najada@gmail.com	test	0697548111	FEMALE	1967-04-04	54	\N	Administrator	2021-04-29 16:53:57.3758	\N	f
31	Anna	anna@gmail.com	5 maj	0692958743	\N	1989-04-03	32	\N	Administrator	2021-04-29 16:55:38.71695	Administrator	f
32	Fotini Prillo	fotini@gmail.com	5 maj	0692958743	\N	1998-04-24	23	\N	Administrator	2021-04-29 16:56:43.266283	Administrator	f
33	nurse	nurse@gmail.com	5 maj	06954388743	\N	1979-04-22	42	\N	Administrator	2021-04-29 18:46:12.697196	Administrator	t
27	Olta	olta@gmail.com	5 maj	0692958743	\N	1995-04-04	26	\N	Administrator	2021-05-01 15:41:05.79232	Administrator	t
35	Fotini Prillo	fotini@gmail.com	5 maj	069295874322	\N	1980-05-07	40	\N	Administrator	2021-05-01 15:41:27.353667	Administrator	f
34	nurseupdate	nurse@gmail.com	5 maj	06954388743	\N	1979-04-22	42	\N	Administrator	2021-05-01 15:41:32.119573	Administrator	f
36	Fotini Prillo update	fotini@gmail.com	5 maj	0692958743	\N	2021-05-04	0	\N	Administrator	2021-05-01 18:30:55.249153	Administrator	t
28	Fotini Prillo	fotini@gmail.com	5 maj	0692958743	\N	2021-04-04	0	\N	Administrator	2021-05-01 18:39:20.76403	Administrator	t
37	Fotini Prillo	fotini@gmail.com	5 maj	0692958743	\N	2021-04-04	0	\N	Administrator	2021-05-01 18:39:33.480247	Administrator	f
\.


--
-- TOC entry 3128 (class 0 OID 19200)
-- Dependencies: 219
-- Data for Name: prescription; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.prescription (prescription_id, appointment_id) FROM stdin;
26	6
31	3
34	7
\.


--
-- TOC entry 3129 (class 0 OID 19213)
-- Dependencies: 220
-- Data for Name: prescription_medicine; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.prescription_medicine (prescription_id, medicine_id, prescription_medicine_id, duration, dose) FROM stdin;
26	1	46	1	1
26	2	47	1	1
34	2	48	1	1
31	1	36	11	\N
31	2	37	12	12
\.


--
-- TOC entry 3116 (class 0 OID 17726)
-- Dependencies: 207
-- Data for Name: role; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.role (role_id, role_name) FROM stdin;
3	Receptionist
4	Patient
1	Admin
2	Doctor
5	Nurse
\.


--
-- TOC entry 3147 (class 0 OID 0)
-- Dependencies: 212
-- Name: appointment_appointment_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.appointment_appointment_id_seq', 27, true);


--
-- TOC entry 3148 (class 0 OID 0)
-- Dependencies: 202
-- Name: blood_blood_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.blood_blood_id_seq', 1, false);


--
-- TOC entry 3149 (class 0 OID 0)
-- Dependencies: 208
-- Name: department_department_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.department_department_id_seq', 1, false);


--
-- TOC entry 3150 (class 0 OID 0)
-- Dependencies: 216
-- Name: donner_donner_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.donner_donner_id_seq', 1, false);


--
-- TOC entry 3151 (class 0 OID 0)
-- Dependencies: 210
-- Name: employee_employee_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.employee_employee_id_seq', 24, true);


--
-- TOC entry 3152 (class 0 OID 0)
-- Dependencies: 214
-- Name: medicine_medicine_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.medicine_medicine_id_seq', 1, false);


--
-- TOC entry 3153 (class 0 OID 0)
-- Dependencies: 204
-- Name: patient_patient_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.patient_patient_id_seq', 1, false);


--
-- TOC entry 3154 (class 0 OID 0)
-- Dependencies: 200
-- Name: person_person_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.person_person_id_seq', 38, true);


--
-- TOC entry 3155 (class 0 OID 0)
-- Dependencies: 221
-- Name: prescription_medicine_prescription_medicine_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.prescription_medicine_prescription_medicine_id_seq', 48, true);


--
-- TOC entry 3156 (class 0 OID 0)
-- Dependencies: 218
-- Name: prescription_prescription_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.prescription_prescription_id_seq', 34, true);


--
-- TOC entry 3157 (class 0 OID 0)
-- Dependencies: 206
-- Name: role_role_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.role_role_id_seq', 1, false);


--
-- TOC entry 2948 (class 2606 OID 17773)
-- Name: appointment appointment_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.appointment
    ADD CONSTRAINT appointment_pkey PRIMARY KEY (appointment_id);


--
-- TOC entry 2938 (class 2606 OID 17705)
-- Name: blood blood_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.blood
    ADD CONSTRAINT blood_pkey PRIMARY KEY (blood_id);


--
-- TOC entry 2944 (class 2606 OID 17742)
-- Name: department department_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.department
    ADD CONSTRAINT department_pkey PRIMARY KEY (department_id);


--
-- TOC entry 2952 (class 2606 OID 17827)
-- Name: donner donner_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.donner
    ADD CONSTRAINT donner_pkey PRIMARY KEY (donner_id);


--
-- TOC entry 2946 (class 2606 OID 17750)
-- Name: employee employee_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.employee
    ADD CONSTRAINT employee_pkey PRIMARY KEY (employee_id);


--
-- TOC entry 2950 (class 2606 OID 17804)
-- Name: medicine medicine_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.medicine
    ADD CONSTRAINT medicine_pkey PRIMARY KEY (medicine_id);


--
-- TOC entry 2940 (class 2606 OID 17713)
-- Name: patient patient_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.patient
    ADD CONSTRAINT patient_pkey PRIMARY KEY (patient_id);


--
-- TOC entry 2936 (class 2606 OID 17694)
-- Name: person person_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.person
    ADD CONSTRAINT person_pkey PRIMARY KEY (person_id);


--
-- TOC entry 2954 (class 2606 OID 19207)
-- Name: prescription prescription_appointment_id_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.prescription
    ADD CONSTRAINT prescription_appointment_id_key UNIQUE (appointment_id);


--
-- TOC entry 2958 (class 2606 OID 19834)
-- Name: prescription_medicine prescription_medicine_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.prescription_medicine
    ADD CONSTRAINT prescription_medicine_pkey PRIMARY KEY (prescription_medicine_id);


--
-- TOC entry 2960 (class 2606 OID 19829)
-- Name: prescription_medicine prescription_medicine_unique; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.prescription_medicine
    ADD CONSTRAINT prescription_medicine_unique UNIQUE (prescription_id, medicine_id);


--
-- TOC entry 2956 (class 2606 OID 19205)
-- Name: prescription prescription_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.prescription
    ADD CONSTRAINT prescription_pkey PRIMARY KEY (prescription_id);


--
-- TOC entry 2942 (class 2606 OID 17734)
-- Name: role role_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.role
    ADD CONSTRAINT role_pkey PRIMARY KEY (role_id);


--
-- TOC entry 2974 (class 2620 OID 19305)
-- Name: person age; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER age BEFORE INSERT OR UPDATE ON public.person FOR EACH ROW EXECUTE FUNCTION public.age_calc();


--
-- TOC entry 2975 (class 2620 OID 19307)
-- Name: person age2; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER age2 BEFORE INSERT OR UPDATE ON public.person FOR EACH ROW EXECUTE FUNCTION public.age_calc();


--
-- TOC entry 2976 (class 2620 OID 19177)
-- Name: appointment deleted; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER deleted BEFORE INSERT ON public.appointment FOR EACH ROW EXECUTE FUNCTION public.deleted_false();


--
-- TOC entry 2973 (class 2620 OID 19302)
-- Name: person deleted; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER deleted BEFORE INSERT ON public.person FOR EACH ROW EXECUTE FUNCTION public.deleted_false();


--
-- TOC entry 2977 (class 2620 OID 19860)
-- Name: appointment last_updated; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER last_updated BEFORE INSERT OR UPDATE ON public.appointment FOR EACH ROW EXECUTE FUNCTION public.update_modified_column();


--
-- TOC entry 2972 (class 2620 OID 18189)
-- Name: person last_updated; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER last_updated BEFORE INSERT OR UPDATE ON public.person FOR EACH ROW EXECUTE FUNCTION public.update_modified_column();


--
-- TOC entry 2969 (class 2606 OID 19208)
-- Name: prescription appointment_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.prescription
    ADD CONSTRAINT appointment_id FOREIGN KEY (appointment_id) REFERENCES public.appointment(appointment_id);


--
-- TOC entry 2962 (class 2606 OID 17719)
-- Name: patient blood_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.patient
    ADD CONSTRAINT blood_id FOREIGN KEY (blood_id) REFERENCES public.blood(blood_id);


--
-- TOC entry 2965 (class 2606 OID 17761)
-- Name: employee department_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.employee
    ADD CONSTRAINT department_id FOREIGN KEY (department_id) REFERENCES public.department(department_id);


--
-- TOC entry 2967 (class 2606 OID 17779)
-- Name: appointment doctor_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.appointment
    ADD CONSTRAINT doctor_id FOREIGN KEY (doctor_id) REFERENCES public.employee(employee_id);


--
-- TOC entry 2971 (class 2606 OID 19223)
-- Name: prescription_medicine medicine_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.prescription_medicine
    ADD CONSTRAINT medicine_id FOREIGN KEY (medicine_id) REFERENCES public.medicine(medicine_id);


--
-- TOC entry 2966 (class 2606 OID 17774)
-- Name: appointment patient_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.appointment
    ADD CONSTRAINT patient_id FOREIGN KEY (patient_id) REFERENCES public.patient(patient_id);


--
-- TOC entry 2961 (class 2606 OID 17714)
-- Name: patient person_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.patient
    ADD CONSTRAINT person_id FOREIGN KEY (person_id) REFERENCES public.person(person_id);


--
-- TOC entry 2963 (class 2606 OID 17751)
-- Name: employee person_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.employee
    ADD CONSTRAINT person_id FOREIGN KEY (person_id) REFERENCES public.person(person_id);


--
-- TOC entry 2968 (class 2606 OID 17828)
-- Name: donner person_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.donner
    ADD CONSTRAINT person_id FOREIGN KEY (person_id) REFERENCES public.person(person_id);


--
-- TOC entry 2970 (class 2606 OID 19218)
-- Name: prescription_medicine prescription_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.prescription_medicine
    ADD CONSTRAINT prescription_id FOREIGN KEY (prescription_id) REFERENCES public.prescription(prescription_id);


--
-- TOC entry 2964 (class 2606 OID 17756)
-- Name: employee role_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.employee
    ADD CONSTRAINT role_id FOREIGN KEY (role_id) REFERENCES public.role(role_id);


-- Completed on 2021-05-04 00:02:07

--
-- PostgreSQL database dump complete
--

