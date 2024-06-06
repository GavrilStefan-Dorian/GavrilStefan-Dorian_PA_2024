create table questions
(
	question_id SERIAL primary key,
	domain_id INTEGER NOT NULL,
	text VARCHAR(255) NOT NULL
);

create table answers
(
	answer_id SERIAL primary key,
	question_id INTEGER NOT NULL,
	text VARCHAR(255) NOT NULL,
	is_correct BOOLEAN NOT NULL
);

create table domains
(
	domain_id SERIAL PRIMARY KEY,
	name VARCHAR(255) NOT NULL
);

create table users
(
	user_id SERIAL PRIMARY KEY,
	username VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
	password VARCHAR(255) NOT NULL	
);

create table quizzes
(
	quiz_id SERIAL PRIMARY KEY,
	domain_id INTEGER REFERENCES domains,
	no_questions INTEGER NOT NULL
);

create table user_responses
(
	response_id SERIAL PRIMARY KEY,
	user_id INTEGER REFERENCES users,
	question_id INTEGER REFERENCES questions,
	answer_id INTEGER REFERENCES answers, 
	quiz_id INTEGER REFERENCES quizzes
);

create table quiz_questions
(
	quiz_question_id SERIAL PRIMARY KEY,
	quiz_id INT REFERENCES quizzes,
	question_id INT REFERENCES questions
);

create table user_score
(
	user_score SERIAL PRIMARY KEY,
	user_id INT REFERENCES users,
	quiz_id INT REFERENCES quizzes,
	score INT NOT NULL
);

INSERT INTO domains (name) VALUES
('GEOGRAFIE'),
('ISTORIE'),
('GASTRONOMIE'),
('STIINTA'),
('LITERATURA'),
('SPORT'),
('MATEMATICA'),
('ARTA'),
('MUZICA'),
('INVENTATORI SI INVENTII');

INSERT INTO questions (domain_id, text) VALUES
((SELECT domain_id FROM domains WHERE name='GEOGRAFIE'), 'Care este cel mai lung fluviu din lume?'),
((SELECT domain_id FROM domains WHERE name='GEOGRAFIE'), 'Care este cel mai lung fluviu din Europa?'),
((SELECT domain_id FROM domains WHERE name='GEOGRAFIE'), 'Care este capitala Australiei?'),
((SELECT domain_id FROM domains WHERE name='GEOGRAFIE'), 'În ce țară se află Muntele Everest?'),
((SELECT domain_id FROM domains WHERE name='GEOGRAFIE'), 'Care este cel mai mare ocean din lume?'),
((SELECT domain_id FROM domains WHERE name='GEOGRAFIE'), 'Ce țară are cea mai mare suprafață de deșert?'),
((SELECT domain_id FROM domains WHERE name='GEOGRAFIE'), 'Care este cel mai înalt vârf montan din Africa?'),
((SELECT domain_id FROM domains WHERE name='GEOGRAFIE'), 'Care țară este cunoscută pentru Marele Zid Chinezesc?'),
((SELECT domain_id FROM domains WHERE name='GEOGRAFIE'), 'Ce țară se află la nord de Coreea de Sud?'),
((SELECT domain_id FROM domains WHERE name='GEOGRAFIE'), 'Care este cea mai mare insulă din lume?'),
((SELECT domain_id FROM domains WHERE name='GEOGRAFIE'), 'Ce fluviu traversează orașul Paris?'),
((SELECT domain_id FROM domains WHERE name='GEOGRAFIE'), 'În ce țară se află orașul istoric Machu Picchu?'),
((SELECT domain_id FROM domains WHERE name='GEOGRAFIE'), 'Cum mai este numit orașul Iași?'),
((SELECT domain_id FROM domains WHERE name='GEOGRAFIE'), 'Ce capitală europeană este cunoscută și sub numele de „Orașul Luminilor”?'),
((SELECT domain_id FROM domains WHERE name='GEOGRAFIE'), 'Care este cea mai mică țară din lume după suprafață?'),
((SELECT domain_id FROM domains WHERE name='GEOGRAFIE'), 'În ce continent se află deșertul Sahara?'),
((SELECT domain_id FROM domains WHERE name='GEOGRAFIE'), 'Care este capitala Canadei?'),
((SELECT domain_id FROM domains WHERE name='GEOGRAFIE'), 'Ce țară are cele mai multe fusuri orare?'),
((SELECT domain_id FROM domains WHERE name='GEOGRAFIE'), 'Care este cel mai mare arhipelag din lume?'),
((SELECT domain_id FROM domains WHERE name='GEOGRAFIE'), 'Ce lac este cunoscut ca fiind cel mai adânc din lume?');

INSERT INTO questions (domain_id, text) VALUES
((SELECT domain_id FROM domains WHERE name='ISTORIE'), 'Cine a fost primul președinte al Statelor Unite ale Americii?'),
((SELECT domain_id FROM domains WHERE name='ISTORIE'), 'În ce an a căzut Imperiul Roman de Apus?'),
((SELECT domain_id FROM domains WHERE name='ISTORIE'), 'Cine a descoperit America în 1492?'),
((SELECT domain_id FROM domains WHERE name='ISTORIE'), 'Cine a fost regina Angliei în timpul epocii elisabetane?'),
((SELECT domain_id FROM domains WHERE name='ISTORIE'), 'Ce eveniment istoric a avut loc pe 6 iunie 1944?'),
((SELECT domain_id FROM domains WHERE name='ISTORIE'), 'Cine a fost conducătorul Uniunii Sovietice în timpul celui de-al Doilea Război Mondial?'),
((SELECT domain_id FROM domains WHERE name='ISTORIE'), 'Ce război a avut loc între 1950 și 1953?'),
((SELECT domain_id FROM domains WHERE name='ISTORIE'), 'Cine a fost primul împărat al Romei?'),
((SELECT domain_id FROM domains WHERE name='ISTORIE'), 'Care a fost prima civilizație care a folosit scrisul cuneiform?'),
((SELECT domain_id FROM domains WHERE name='ISTORIE'), 'În ce an a avut loc Revoluția Franceză?'),
((SELECT domain_id FROM domains WHERE name='ISTORIE'), 'Ce zid a fost construit pentru a separa Germania de Est și Germania de Vest?'),
((SELECT domain_id FROM domains WHERE name='ISTORIE'), 'Cine a fost liderul mișcării pentru drepturile civile din SUA care a ținut faimosul discurs „I Have a Dream”?'),
((SELECT domain_id FROM domains WHERE name='ISTORIE'), 'Care civilizație antică a construit Machu Picchu?'),
((SELECT domain_id FROM domains WHERE name='ISTORIE'), 'În ce an a avut loc Revoluția Rusă?'),
((SELECT domain_id FROM domains WHERE name='ISTORIE'), 'Cine a fost primul cancelar al Germaniei reunificate?'),
((SELECT domain_id FROM domains WHERE name='ISTORIE'), 'Cine a fost conducătorul Mongoliei care a creat unul dintre cele mai mari imperii din istorie?'),
((SELECT domain_id FROM domains WHERE name='ISTORIE'), 'Ce oraș a fost distrus de o erupție vulcanică în 79 d.Hr.?'),
((SELECT domain_id FROM domains WHERE name='ISTORIE'), 'Cine a scris „Declarația de Independență” a Statelor Unite?'),
((SELECT domain_id FROM domains WHERE name='ISTORIE'), 'Ce bătălie a marcat sfârșitul dominației napoleoniene în Europa?'),
((SELECT domain_id FROM domains WHERE name='ISTORIE'), 'În ce an s-a semnat Tratatul de la Versailles, care a pus capăt Primului Război Mondial?');

INSERT INTO questions (domain_id, text) VALUES
((SELECT domain_id FROM domains WHERE name='GASTRONOMIE'), 'Ce fel de brânză se folosește tradițional în tiramisu?'),
((SELECT domain_id FROM domains WHERE name='GASTRONOMIE'), 'Care este principalul ingredient în hummus?'),
((SELECT domain_id FROM domains WHERE name='GASTRONOMIE'), 'Ce fruct este cunoscut ca „regele fructelor” în Asia de Sud-Est?'),
((SELECT domain_id FROM domains WHERE name='GASTRONOMIE'), 'Din ce țară provine mâncarea „paella”?'),
((SELECT domain_id FROM domains WHERE name='GASTRONOMIE'), 'Ce tip de carne este folosită în preparatul tradițional britanic „shepherd’s pie”?'),
((SELECT domain_id FROM domains WHERE name='GASTRONOMIE'), 'Ce condiment este cunoscut ca „aurul roșu” din cauza prețului său ridicat?'),
((SELECT domain_id FROM domains WHERE name='GASTRONOMIE'), 'Ce fel de paste au formă de tuburi lungi și groase?'),
((SELECT domain_id FROM domains WHERE name='GASTRONOMIE'), 'Ce băutură alcoolică este obținută din distilarea vinului?'),
((SELECT domain_id FROM domains WHERE name='GASTRONOMIE'), 'Ce fel de nucă este utilizată pentru a face marțipan?'),
((SELECT domain_id FROM domains WHERE name='GASTRONOMIE'), 'Ce ingredient dă culoarea galbenă preparatului „risotto alla milanese”?'),
((SELECT domain_id FROM domains WHERE name='GASTRONOMIE'), 'Din ce țară provine „mozzarella”?'),
((SELECT domain_id FROM domains WHERE name='GASTRONOMIE'), 'Ce fruct de mare este principal în preparatul „bouillabaisse”?'),
((SELECT domain_id FROM domains WHERE name='GASTRONOMIE'), 'Ce legumă este principală în „ratatouille”?'),
((SELECT domain_id FROM domains WHERE name='GASTRONOMIE'), 'Ce fel de mâncare este „guacamole”?'),
((SELECT domain_id FROM domains WHERE name='GASTRONOMIE'), 'Ce tip de carne este folosită în preparatul „bistecca alla fiorentina”?'),
((SELECT domain_id FROM domains WHERE name='GASTRONOMIE'), 'Ce băutură alcoolică se folosește în „Irish coffee”?'),
((SELECT domain_id FROM domains WHERE name='GASTRONOMIE'), 'Ce fel de brânză este utilizată în preparatul grecesc „saganaki”?'),
((SELECT domain_id FROM domains WHERE name='GASTRONOMIE'), 'Ce fruct este ingredient principal în „banoffee pie”?'),
((SELECT domain_id FROM domains WHERE name='GASTRONOMIE'), 'Ce fel de pește este folosit în preparatul „ceviche”?'),
((SELECT domain_id FROM domains WHERE name='GASTRONOMIE'), 'Din ce țară provine desertul „baklava”?'),
((SELECT domain_id FROM domains WHERE name='GASTRONOMIE'), 'Ce fel de carne este folosită în preparatul „coq au vin”?'),
((SELECT domain_id FROM domains WHERE name='GASTRONOMIE'), 'Ce ingredient este principal în „tzatziki”?'),
((SELECT domain_id FROM domains WHERE name='GASTRONOMIE'), 'Ce fruct este folosit pentru a face „kir royale”?'),
((SELECT domain_id FROM domains WHERE name='GASTRONOMIE'), 'Ce tip de paste sunt sub formă de fundițe?'),
((SELECT domain_id FROM domains WHERE name='GASTRONOMIE'), 'Ce ingredient se folosește pentru a prepara „pesto”?'),
((SELECT domain_id FROM domains WHERE name='GASTRONOMIE'), 'Ce băutură este considerată „națională” în Rusia?'),
((SELECT domain_id FROM domains WHERE name='GASTRONOMIE'), 'Ce legumă este principală în „baba ghanoush”?'),
((SELECT domain_id FROM domains WHERE name='GASTRONOMIE'), 'Ce fel de brânză se utilizează în „Greek salad”?'),
((SELECT domain_id FROM domains WHERE name='GASTRONOMIE'), 'Ce ingredient se folosește pentru a face „tapenade”?'),
((SELECT domain_id FROM domains WHERE name='GASTRONOMIE'), 'Ce fruct se foloseste de obicei in tortul Dobos');

INSERT INTO questions (domain_id, text) VALUES
((SELECT domain_id FROM domains WHERE name='STIINTA'), 'Cine a propus teoria relativității generale?'),
((SELECT domain_id FROM domains WHERE name='STIINTA'), 'Care este elementul chimic cu simbolul O?'),
((SELECT domain_id FROM domains WHERE name='STIINTA'), 'Ce planetă este cunoscută ca "Planeta Roșie"?'),
((SELECT domain_id FROM domains WHERE name='STIINTA'), 'Care este cea mai mare planetă din Sistemul Solar?'),
((SELECT domain_id FROM domains WHERE name='STIINTA'), 'Ce tip de celulă are un nucleu definit?'),
((SELECT domain_id FROM domains WHERE name='STIINTA'), 'Cine a descoperit penicilina?'),
((SELECT domain_id FROM domains WHERE name='STIINTA'), 'Ce gaz este esențial pentru respirația umană?'),
((SELECT domain_id FROM domains WHERE name='STIINTA'), 'Ce unitate de măsură este folosită pentru energie?'),
((SELECT domain_id FROM domains WHERE name='STIINTA'), 'Cine a formulat legea gravitației universale?'),
((SELECT domain_id FROM domains WHERE name='STIINTA'), 'Care este cel mai abundent element din univers?'),
((SELECT domain_id FROM domains WHERE name='STIINTA'), 'Ce specie este considerată cea mai inteligentă după om?'),
((SELECT domain_id FROM domains WHERE name='STIINTA'), 'Ce instrument este folosit pentru a măsura presiunea atmosferică?'),
((SELECT domain_id FROM domains WHERE name='STIINTA'), 'Care este formula chimică a apei?'),
((SELECT domain_id FROM domains WHERE name='STIINTA'), 'Ce planetă este cea mai apropiată de Soare?'),
((SELECT domain_id FROM domains WHERE name='STIINTA'), 'Cine a inventat becul cu filament?'),
((SELECT domain_id FROM domains WHERE name='STIINTA'), 'Ce particule sunt responsabile pentru încărcarea electrică în atom?'),
((SELECT domain_id FROM domains WHERE name='STIINTA'), 'Care este organul principal al sistemului nervos central?'),
((SELECT domain_id FROM domains WHERE name='STIINTA'), 'Ce lege afirmă că „energia nu poate fi creată sau distrusă”?'),
((SELECT domain_id FROM domains WHERE name='STIINTA'), 'Cine este cunoscut ca „părintele geneticii”?'),
((SELECT domain_id FROM domains WHERE name='STIINTA'), 'Care este al doilea cel mai abundent gaz din atmosfera Pământului?'),
((SELECT domain_id FROM domains WHERE name='STIINTA'), 'Ce particulă subatomică nu are sarcină electrică?'),
((SELECT domain_id FROM domains WHERE name='STIINTA'), 'Care este unitatea de măsură pentru frecvență?'),
((SELECT domain_id FROM domains WHERE name='STIINTA'), 'Ce planetă are cele mai multe inele?'),
((SELECT domain_id FROM domains WHERE name='STIINTA'), 'Cine a descoperit legea flotabilității?'),
((SELECT domain_id FROM domains WHERE name='STIINTA'), 'Care este cel mai greu element natural?'),
((SELECT domain_id FROM domains WHERE name='STIINTA'), 'Ce organ al corpului uman produce insulina?'),
((SELECT domain_id FROM domains WHERE name='STIINTA'), 'Cine a descoperit radioactivitatea?'),
((SELECT domain_id FROM domains WHERE name='STIINTA'), 'Ce fenomen este măsurat în grade Celsius?'),
((SELECT domain_id FROM domains WHERE name='STIINTA'), 'Care este simbolul chimic pentru argint?'),
((SELECT domain_id FROM domains WHERE name='STIINTA'), 'Ce planetă este cunoscută pentru cei mai multi vulcani?');

INSERT INTO questions (domain_id, text) VALUES
((SELECT domain_id FROM domains WHERE name='LITERATURA'), 'Cine a scris poemul „Luceafărul”?'),
((SELECT domain_id FROM domains WHERE name='LITERATURA'), 'Care este autorul romanului „Ion”?'),
((SELECT domain_id FROM domains WHERE name='LITERATURA'), 'Care este scriitorul cunoscut pentru ciclul de romane „Moromeții”?'),
((SELECT domain_id FROM domains WHERE name='LITERATURA'), 'Cine a scris romanul „Baltagul”?'),
((SELECT domain_id FROM domains WHERE name='LITERATURA'), 'Ce poet român este considerat cel mai mare poet național?'),
((SELECT domain_id FROM domains WHERE name='LITERATURA'), 'Cine a scris „Enigma Otiliei”?'),
((SELECT domain_id FROM domains WHERE name='LITERATURA'), 'Ce scriitor român a fost laureat al Premiului Nobel pentru Literatură în 2009?'),
((SELECT domain_id FROM domains WHERE name='LITERATURA'), 'Care este autorul romanului „Pădurea spânzuraților”?'),
((SELECT domain_id FROM domains WHERE name='LITERATURA'), 'Ce scriitor român a scris „Maitreyi”?'),
((SELECT domain_id FROM domains WHERE name='LITERATURA'), 'Cine a scris „Moara cu noroc”?'),
((SELECT domain_id FROM domains WHERE name='LITERATURA'), 'Care este autorul poeziei „Plumb”?'),
((SELECT domain_id FROM domains WHERE name='LITERATURA'), 'Cine a scris „Scrisoarea III”?'),
((SELECT domain_id FROM domains WHERE name='LITERATURA'), 'Cine a scris „La țigănci”?'),
((SELECT domain_id FROM domains WHERE name='LITERATURA'), 'Care este autorul romanului „Craii de Curtea-Veche”?'),
((SELECT domain_id FROM domains WHERE name='LITERATURA'), 'Cine a scris „Ultima noapte de dragoste, întâia noapte de război”?'),
((SELECT domain_id FROM domains WHERE name='LITERATURA'), 'Ce scriitor a creat personajul Mihai Eminescu în opera „Sărmanul Dionis”?'),
((SELECT domain_id FROM domains WHERE name='LITERATURA'), 'Cine a scris „Cel mai iubit dintre pământeni”?'),
((SELECT domain_id FROM domains WHERE name='LITERATURA'), 'Cine este autorul nuvelei „Hanu Ancuței”?'),
((SELECT domain_id FROM domains WHERE name='LITERATURA'), 'Care este autorul romanului „Desculț”?'),
((SELECT domain_id FROM domains WHERE name='LITERATURA'), 'Cine a scris „Amintiri din copilărie”?');

INSERT INTO questions (domain_id, text) VALUES
((SELECT domain_id FROM domains WHERE name='SPORT'), 'Cine este considerat cel mai mare fotbalist al tuturor timpurilor?'),
((SELECT domain_id FROM domains WHERE name='SPORT'), 'Care este sportul național al Japoniei?'),
((SELECT domain_id FROM domains WHERE name='SPORT'), 'În ce an s-au desfășurat primele Jocuri Olimpice moderne?'),
((SELECT domain_id FROM domains WHERE name='SPORT'), 'Cine deține recordul pentru cele mai multe titluri de Grand Slam la tenis masculin?'),
((SELECT domain_id FROM domains WHERE name='SPORT'), 'Care este echipa de fotbal cu cele mai multe titluri în Liga Campionilor UEFA?'),
((SELECT domain_id FROM domains WHERE name='SPORT'), 'Cine este considerat cel mai bun jucător de baschet din toate timpurile?'),
((SELECT domain_id FROM domains WHERE name='SPORT'), 'Care este durata unui meci de fotbal standard?'),
((SELECT domain_id FROM domains WHERE name='SPORT'), 'În ce oraș s-au desfășurat Jocurile Olimpice din 2008?'),
((SELECT domain_id FROM domains WHERE name='SPORT'), 'Cine este cunoscut ca fiind cel mai rapid om din lume?'),
((SELECT domain_id FROM domains WHERE name='SPORT'), 'Ce țară a câștigat Cupa Mondială la fotbal în 2018?'),
((SELECT domain_id FROM domains WHERE name='SPORT'), 'Cine este jucătorul de tenis cu cele mai multe titluri de Grand Slam la tenis feminin?'),
((SELECT domain_id FROM domains WHERE name='SPORT'), 'Ce sport este asociat cu jucătorul legendar Michael Jordan?'),
((SELECT domain_id FROM domains WHERE name='SPORT'), 'Cine este cunoscut pentru lovitura de grație în box, denumită „The Rumble in the Jungle”?'),
((SELECT domain_id FROM domains WHERE name='SPORT'), 'Care este durata unei reprize în boxul profesionist?'),
((SELECT domain_id FROM domains WHERE name='SPORT'), 'Cine este considerat cel mai mare ciclist al tuturor timpurilor?'),
((SELECT domain_id FROM domains WHERE name='SPORT'), 'Care este suprafața unui teren de fotbal standard?'),
((SELECT domain_id FROM domains WHERE name='SPORT'), 'Ce echipă de fotbal american a câștigat cele mai multe Super Bowl-uri?'),
((SELECT domain_id FROM domains WHERE name='SPORT'), 'Cine este jucătorul de golf cu cele mai multe victorii în turneele majore?'),
((SELECT domain_id FROM domains WHERE name='SPORT'), 'Ce țară găzduiește cursa de Formula 1 de la Monza?'),
((SELECT domain_id FROM domains WHERE name='SPORT'), 'Care este distanța oficială a unui maraton?');

INSERT INTO questions (domain_id, text) VALUES
((SELECT domain_id FROM domains WHERE name='MATEMATICA'), 'Care este rezultatul înmulțirii lui 7 cu 8?'),
((SELECT domain_id FROM domains WHERE name='MATEMATICA'), 'Câte laturi are un pătrat?'),
((SELECT domain_id FROM domains WHERE name='MATEMATICA'), 'Ce reprezintă π în matematică?'),
((SELECT domain_id FROM domains WHERE name='MATEMATICA'), 'Ce număr este reprezentat de cifra romană "D"?'),
((SELECT domain_id FROM domains WHERE name='MATEMATICA'), 'Care este rădăcina pătrată a lui 144?'),
((SELECT domain_id FROM domains WHERE name='MATEMATICA'), 'Ce este suma tuturor unghiurilor interioare ale unui triunghi?'),
((SELECT domain_id FROM domains WHERE name='MATEMATICA'), 'Ce număr este opusul lui -5?'),
((SELECT domain_id FROM domains WHERE name='MATEMATICA'), 'Câte grade Fahrenheit sunt echivalente cu 0 grade Celsius?'),
((SELECT domain_id FROM domains WHERE name='MATEMATICA'), 'Ce este derivata unei funcții în cadrul calculului diferențial?'),
((SELECT domain_id FROM domains WHERE name='MATEMATICA'), 'Ce număr este egal cu 10 la puterea a treia?'),
((SELECT domain_id FROM domains WHERE name='MATEMATICA'), 'Care este cel mai mic număr prim mai mare decât 50?'),
((SELECT domain_id FROM domains WHERE name='MATEMATICA'), 'Ce este logaritmul unui număr?'),
((SELECT domain_id FROM domains WHERE name='MATEMATICA'), 'Cât fac 3/4 plus 1/2?'),
((SELECT domain_id FROM domains WHERE name='MATEMATICA'), 'Care este al șaptelea număr prim?'),
((SELECT domain_id FROM domains WHERE name='MATEMATICA'), 'Ce este teorema lui Pitagora?');

INSERT INTO questions (domain_id, text) VALUES
((SELECT domain_id FROM domains WHERE name='ARTA'), 'Care este cea mai faimoasă pictură a lui Leonardo da Vinci?'),
((SELECT domain_id FROM domains WHERE name='ARTA'), 'Cine a sculptat statuia "David"?'),
((SELECT domain_id FROM domains WHERE name='ARTA'), 'Ce perioadă artistică este caracterizată de operele lui Michelangelo și Rafael?'),
((SELECT domain_id FROM domains WHERE name='ARTA'), 'Care este cea mai cunoscută operă a lui Vincent van Gogh?'),
((SELECT domain_id FROM domains WHERE name='ARTA'), 'Cine a pictat "Noaptea înstelată"?'),
((SELECT domain_id FROM domains WHERE name='ARTA'), 'Ce curent artistic este asociat cu operele lui Claude Monet și Edgar Degas?'),
((SELECT domain_id FROM domains WHERE name='ARTA'), 'Cine a pictat "Sfânta Cina"?'),
((SELECT domain_id FROM domains WHERE name='ARTA'), 'Care este numele compozitorului care a scris "Simfonia a 5-a"?'),
((SELECT domain_id FROM domains WHERE name='ARTA'), 'Ce artist este cunoscut pentru sculpturile sale abstracte din metal?'),
((SELECT domain_id FROM domains WHERE name='ARTA'), 'Care este numele pictorului care a creat "Nebunul din iulie"?'),
((SELECT domain_id FROM domains WHERE name='ARTA'), 'Cine a pictat "Nașterea lui Venus"?'),
((SELECT domain_id FROM domains WHERE name='ARTA'), 'Care pictor este cunoscut pentru lucrările sale cu ceasuri topite, precum "Persistența memoriei"?'),
((SELECT domain_id FROM domains WHERE name='ARTA'), 'Ce artist este asociat cu mișcarea suprarealistă?'),
((SELECT domain_id FROM domains WHERE name='ARTA'), 'Care este numele sculptorului italian care a realizat statuia lui "David"?'),
((SELECT domain_id FROM domains WHERE name='ARTA'), 'Cine a pictat "Pictorul și modelul său"?'),
((SELECT domain_id FROM domains WHERE name='ARTA'), 'Ce perioadă artistică este caracterizată de operele lui Rembrandt și Vermeer?'),
((SELECT domain_id FROM domains WHERE name='ARTA'), 'Care este numele pictorului cunoscut pentru tabloul "Grita"?'),
((SELECT domain_id FROM domains WHERE name='ARTA'), 'Cine este sculptorul francez care a realizat "The Thinker"?'),
((SELECT domain_id FROM domains WHERE name='ARTA'), 'Care este numele pictorului francez cunoscut pentru tabloul "Femeia cu un evantai"?'),
((SELECT domain_id FROM domains WHERE name='ARTA'), 'Cine este considerat părintele sculpturii moderne?');

INSERT INTO questions (domain_id, text) VALUES
((SELECT domain_id FROM domains WHERE name='MUZICA'), 'Care formație a lansat albumul "The Dark Side of the Moon"?'),
((SELECT domain_id FROM domains WHERE name='MUZICA'), 'Cine a compus simfonia "Oda bucuriei"?'),
((SELECT domain_id FROM domains WHERE name='MUZICA'), 'Care instrument muzical este asociat cu Johann Sebastian Bach?'),
((SELECT domain_id FROM domains WHERE name='MUZICA'), 'Ce artist a cântat hitul "Thriller"?'),
((SELECT domain_id FROM domains WHERE name='MUZICA'), 'Care este numele celebrului compozitor de operă italian cunoscut pentru opere precum "La traviata" și "Rigoletto"?'),
((SELECT domain_id FROM domains WHERE name='MUZICA'), 'Ce formație rock a lansat albumul "Abbey Road"?'),
((SELECT domain_id FROM domains WHERE name='MUZICA'), 'Cine este supranumit "Regele muzicii pop"?'),
((SELECT domain_id FROM domains WHERE name='MUZICA'), 'Care este instrumentul principal într-un cvartet de coarde?'),
((SELECT domain_id FROM domains WHERE name='MUZICA'), 'Cine a scris "Simfonia a 9-a"?'),
((SELECT domain_id FROM domains WHERE name='MUZICA'), 'Ce compozitor este cunoscut pentru lucrările sale "Für Elise" și "Simfonia a 5-a"?'),
((SELECT domain_id FROM domains WHERE name='MUZICA'), 'Ce instrument este asociat cu Eric Clapton?'),
((SELECT domain_id FROM domains WHERE name='MUZICA'), 'Cine a compus "Canonul în Re major"?'),
((SELECT domain_id FROM domains WHERE name='MUZICA'), 'Care este cel mai bine vândut artist solo din toate timpurile?'),
((SELECT domain_id FROM domains WHERE name='MUZICA'), 'Ce artist a lansat albumul "Purple Rain"?'),
((SELECT domain_id FROM domains WHERE name='MUZICA'), 'Cine a scris opera "Carmen"?'),
((SELECT domain_id FROM domains WHERE name='MUZICA'), 'Ce formație a lansat albumul "Sgt. Pepper''s Lonely Hearts Club Band"?'),
((SELECT domain_id FROM domains WHERE name='MUZICA'), 'Care este numele celei mai mari orchestre simfonice din lume?'),
((SELECT domain_id FROM domains WHERE name='MUZICA'), 'Cine este supranumit "Regele Rock ''n'' Roll"?'),
((SELECT domain_id FROM domains WHERE name='MUZICA'), 'Ce compozitor este cunoscut pentru lucrările sale "Rhapsody in Blue" și "Un american la Paris"?'),
((SELECT domain_id FROM domains WHERE name='MUZICA'), 'Cine este cântăreața cunoscută pentru hitul "Like a Virgin"?');

INSERT INTO questions (domain_id, text) VALUES
((SELECT domain_id FROM domains WHERE name='INVENTATORI SI INVENTII'), 'Care inventator a patentat becul electric?'),
((SELECT domain_id FROM domains WHERE name='INVENTATORI SI INVENTII'), 'Cine a inventat primul telefon?'),
((SELECT domain_id FROM domains WHERE name='INVENTATORI SI INVENTII'), 'Ce inventator este cunoscut pentru invenția rachetei spațiale?'),
((SELECT domain_id FROM domains WHERE name='INVENTATORI SI INVENTII'), 'Care este numele inventatorului ce a creat primul automobil?'),
((SELECT domain_id FROM domains WHERE name='INVENTATORI SI INVENTII'), 'Cine este inventatorul cunoscut pentru invenția imprimantei?'),
((SELECT domain_id FROM domains WHERE name='INVENTATORI SI INVENTII'), 'Ce inventator a patentat primul avion cu motor?'),
((SELECT domain_id FROM domains WHERE name='INVENTATORI SI INVENTII'), 'Care este numele inventatorului care a creat tehnologia wireless (fără fir)?'),
((SELECT domain_id FROM domains WHERE name='INVENTATORI SI INVENTII'), 'Cine a inventat primul computer personal?'),
((SELECT domain_id FROM domains WHERE name='INVENTATORI SI INVENTII'), 'Ce inventator este asociat cu invenția becului fluorescent?'),
((SELECT domain_id FROM domains WHERE name='INVENTATORI SI INVENTII'), 'Care este numele inventatorului ce a creat primul telescop refractor?'),
((SELECT domain_id FROM domains WHERE name='INVENTATORI SI INVENTII'), 'Cine este cunoscut pentru invenția radioului?'),
((SELECT domain_id FROM domains WHERE name='INVENTATORI SI INVENTII'), 'Ce inventator a patentat prima mașină de cusut?'),
((SELECT domain_id FROM domains WHERE name='INVENTATORI SI INVENTII'), 'Care este numele inventatorului asociat cu descoperirea penicilinei?'),
((SELECT domain_id FROM domains WHERE name='INVENTATORI SI INVENTII'), 'Cine a inventat primul aparat de fotografiat?'),
((SELECT domain_id FROM domains WHERE name='INVENTATORI SI INVENTII'), 'Ce inventator este cunoscut pentru invenția motorului cu abur?');

SELECT * FROM QUESTIONS;
select * from domains;
delete from questions where domain_id=10;
SELECT pg_get_serial_sequence('questions', 'question_id');
ALTER SEQUENCE public.questions_question_id_seq RESTART WITH 196;

INSERT INTO answers (text, is_correct, question_id) VALUES
('Amazon', true, 1),('Nil', false, 1),('Yangtze', false, 1),('Mississippi', false, 1),
('Volga', true, 2),('Dunărea', false, 2),('Rinul', false, 2),('Elba', false, 2),
('Sydney', false, 3),('Melbourne', false, 3),('Canberra', true, 3),('Brisbane', false, 3),
('China', false, 4),('India', false, 4),('Nepal', true, 4),('Bhutan', false, 4),('Oceanul Atlantic', false, 5),('Oceanul Indian', false, 5),('Oceanul Arctic', false, 5),('Oceanul Pacific', true, 5),
('Australia', false, 6),('Arabia Saudită', false, 6),('China', false, 6),('Algeria', true, 6),
('Muntele Kenya', false, 7),('Muntele Kilimanjaro', true, 7),('Muntele Elgon', false, 7),('Muntele Meru', false, 7),
('Japonia', false, 8),('China', true, 8),('Coreea de Sud', false, 8),('Mongolia', false, 8),
('China', false, 9),('Japonia', false, 9),('Coreea de Nord', true, 9),('Mongolia', false, 9),
('Groenlanda', true, 10),('Madagascar', false, 10),('Borneo', false, 10),('Sumatra', false, 10),
('Tamisa', false, 11),('Dunărea', false, 11),('Sena', true, 11),('Elba', false, 11),
('Mexic', false, 12),('Peru', true, 12),('Chile', false, 12),('Bolivia', false, 12),
('Orașul de pe Bega', false, 13),('Orașul Celor 7 Coline', true, 13),('Orașul Unirii', false, 13),('Orașul Trandafirilor', false, 13),
('Londra', false, 14),('Roma', false, 14),('Paris', true, 14),('Viena', false, 14),
('Monaco', false, 15),('San Marino', false, 15),('Liechtenstein', false, 15),('Vatican', true,15),
('Asia', false, 16),('America de Sud', false, 16),('Africa', true, 16),('Australia', false, 16),
('Toronto', false, 17),('Vancouver', false, 17),('Ottawa', true, 17),('Montreal', false, 17),
('Statele Unite ale Americii', false, 18),('Rusia', true, 18),('Brazilia', false, 18),('Canada', false, 18),
('Filipine', false, 19),('Arhipelagul Malay', true, 19),('Arhipelagul Canadian', false, 19),('Insulele Britanice', false, 19),
('Lacul Baikal', true, 20),('Lacul Superior', false, 20),('Lacul Victoria', false, 20),('Lacul Tanganyika', false, 20);

INSERT INTO answers (text, is_correct, question_id) VALUES
('George Washington', true, 21),('Thomas Jefferson', false, 21),('John Adams', false, 21),('James Madison', false, 21),
('476', true, 22),('410', false, 22),('509', false, 22),('533', false, 22),
('Cristofor Columb', true, 23),('Ferdinand Magellan', false, 23),('James Cook', false, 23),('Amerigo Vespucci', false, 23),
('Elisabeta I', true, 24),('Maria I', false, 24),('Victoria', false, 24),('Ana', false, 24),
('Ziua Z', true, 25),('Atacul de la Pearl Harbor', false, 25),('Bătălia de la Midway', false, 25),('Sfârșitul celui de-al Doilea Război Mondial', false, 25),
('Iosif Stalin', true, 26),('Nikita Hrușciov', false, 26),('Vladimir Lenin', false, 26),('Mihail Gorbaciov', false, 26),
('Războiul din Coreea', true, 27),('Războiul din Vietnam', false, 27),('Războiul Rece', false, 27),('Războiul Civil American', false, 27),
('Augustus', true, 28),('Cezar', false, 28),('Nero', false, 28),('Traian', false, 28),
('Sumerienii', true, 29),('Egiptenii', false, 29),('Fenicienii', false, 29),('Perșii', false, 29),
('1789', true, 30),('1776', false, 30),('1812', false, 30),('1848', false, 30),
('Zidul Berlinului', true, 31),('Zidul Chinezesc', false, 31),('Zidul Atlanticului', false, 31),('Zidul Germaniei', false, 31),
('Martin Luther King Jr.', true, 32),('Malcolm X', false, 32),('Rosa Parks', false, 32),('Booker T. Washington', false, 32),
('Incașii', true, 33),('Aztecii', false, 33),('Maiașii', false, 33),('Olmecii', false, 33),
('1917', true, 34),('1905', false, 34),('1914', false, 34),('1922', false, 34),
('Helmut Kohl', true, 35),('Willy Brandt', false, 35),('Gerhard Schröder', false, 35),('Angela Merkel', false, 35),
('Genghis Khan', true, 36),('Attila', false, 36),('Tamerlan', false, 36),('Kublai Khan', false, 36),
('Pompeii', true, 37),('Herculaneum', false, 37),('Carthage', false, 37),('Alexandria', false, 37),
('Thomas Jefferson', true, 38),('Benjamin Franklin', false, 38),('John Adams', false, 38),('George Washington', false, 38),
('Bătălia de la Waterloo', true, 39),('Bătălia de la Trafalgar', false, 39),('Bătălia de la Leipzig', false, 39),('Bătălia de la Borodino', false, 39),
('1919', true, 40),('1917', false, 40),('1918', false, 40),('1920', false, 40);


INSERT INTO answers (text, is_correct, question_id) VALUES
('Ricotta', false, 41), ('Mascarpone', true, 41), ('Mozarella', false, 41), ('Parmigiano-Reggiano', false, 41),
('Linte', false, 42), ('Mazăre', false, 42), ('Năut', true, 42), ('Fasole', false, 42),
('Mango', false, 43), ('Lychee', false, 43), ('Rambutan', false, 43), ('Durian', true, 43),
('Portugalia', false, 44), ('Italia', false, 44), ('Spania', true, 44), ('Franța', false, 44),
('Pui', false, 45), ('Miel', true, 45), ('Vită', false, 45), ('Porc', false, 45),
('Șofran', true, 46), ('Scorțișoară', false, 46), ('Vanilie', false, 46), ('Cardamom', false, 46),
('Penne', false, 47), ('Spaghetti', false, 47), ('Fettuccine', false, 47), ('Cannelloni', true, 47),
('Gin', false, 48), ('Coniac', true, 48), ('Vodka', false, 48), ('Whisky', false, 48),
('Caju', false, 49), ('Migdale', true, 49), ('Alune', false, 49), ('Nuci', false, 49),
('Paprika', false, 50), ('Turmeric', false, 50), ('Curry', false, 50), ('Șofran', true, 50),
('Grecia', false, 51), ('Franța', false, 51), ('Italia', true, 51), ('Spania', false, 51),
('Calmar', false, 52), ('Creveți', false, 52), ('Pește', true, 52), ('Homar', false, 52),
('Roșii', false, 53), ('Vinete', true, 53), ('Ceapă', false, 53), ('Dovlecei', false, 53),
('Fel principal', false, 54), ('Supă', false, 54), ('Sos', true, 54), ('Desert', false, 54),
('Miel', false, 55), ('Pui', false, 55), ('Vită', true, 55), ('Porc', false, 55),
('Tequila', false, 56), ('Whisky', true, 56), ('Vodka', false, 56), ('Rom', false, 56),
('Gouda', false, 57), ('Brie', false, 57), ('Feta', true, 57), ('Mozarella', false, 57),
('Căpșuni', false, 58), ('Mere', false, 58), ('Pere', false, 58), ('Banane', true, 58),
('Hering', false, 59), ('Ton', false, 59), ('Cod', true, 59), ('Somon', false, 59),
('Grecia', false, 60), ('Liban', false, 60), ('Egipt', false, 60), ('Turcia', true, 60),
('Miel', false, 61), ('Porc', false, 61), ('Vită', false, 61), ('Pui', true, 61),
('Roșii', false, 62), ('Brânză', false, 62), ('Castraveți', true, 62), ('Cartofi', false, 62),
('Căpșuni', false, 63), ('Zmeură', false, 63), ('Coacăze', true, 63), ('Mure', false, 63),
('Linguine', false, 64), ('Rotini', false, 64), ('Fusilli', false, 64), ('Farfalle', true, 64),
('Spanac', false, 65), ('Ardei gras', false, 65), ('Busuioc', true, 65), ('Roșii', false, 65),
('Tequila', false, 66), ('Whisky', false, 66), ('Vodka', true, 66), ('Rom', false, 66),
('Roșii', false, 67), ('Vinete', true, 67), ('Ardei', false, 67), ('Castraveți', false, 67),
('Ricotta', false, 68), ('Mozarella', false, 68), ('Feta', true, 68), ('Cheddar', false, 68),
('Roșii', false, 69), ('Ardei gras', false, 69), ('Măsline', true, 69), ('Vinete', false, 69),
('Caise', false, 70), ('Ananas', false, 70), ('Visine', true, 70), ('Lămâie', false, 70);

INSERT INTO answers (text, is_correct, question_id) VALUES
('Albert Einstein', true, 71), ('Isaac Newton', false, 71), ('Nikola Tesla', false, 71), ('Galileo Galilei', false, 71),
('Oțel', false, 72), ('Aur', false, 72), ('Oxigen', true, 72), ('Argon', false, 72),
('Venus', false, 73), ('Marte', true, 73), ('Jupiter', false, 73), ('Saturn', false, 73),
('Jupiter', true, 74), ('Saturn', false, 74), ('Neptun', false, 74), ('Uranus', false, 74),
('Eucariote', true, 75), ('Procariote', false, 75), ('Arhee', false, 75), ('Bacterii', false, 75),
('Marie Curie', false, 76), ('Alexander Fleming', true, 76), ('Louis Pasteur', false, 76), ('Robert Koch', false, 76),
('Azot', false, 77), ('Hidrogen', false, 77), ('Oxigen', true, 77), ('Dioxid de carbon', false, 77),
('Joule', true, 78), ('Watt', false, 78), ('Volt', false, 78), ('Amper', false, 78),
('Galileo Galilei', false, 79), ('Isaac Newton', true, 79), ('Johannes Kepler', false, 79), ('Albert Einstein', false, 79),
('Hidrogen', true, 80), ('Oxigen', false, 80), ('Carbon', false, 80), ('Heliu', false, 80),
('Delfin', true, 81), ('Chimpanzeu', false, 81), ('Elefant', false, 81), ('Câine', false, 81),
('Barometru', true, 82), ('Termometru', false, 82), ('Anemometru', false, 82), ('Higrometru', false, 82),
('H2O', true, 83), ('CO2', false, 83), ('O2', false, 83), ('H2', false, 83),
('Venus', false, 84), ('Mercur', true, 84), ('Marte', false, 84), ('Pământ', false, 84),
('Thomas Edison', true, 85), ('Nikola Tesla', false, 85), ('Benjamin Franklin', false, 85), ('Alexander Graham Bell', false, 85),
('Proton', false, 86), ('Neutron', false, 86), ('Electron', true, 86), ('Fotoni', false, 86),
('Creierul', true, 87), ('Inima', false, 87), ('Plămânii', false, 87), ('Rinichii', false, 87),
('Legea conservării energiei', true, 88), ('Legea lui Boyle', false, 88), ('Legea lui Avogadro', false, 88), ('Legea lui Charles', false, 88),
('Gregor Mendel', true, 89), ('Charles Darwin', false, 89), ('James Watson', false, 89), ('Francis Crick', false, 89),
('Oxigen', false, 90), ('Dioxid de carbon', false, 90), ('Azot', true, 90), ('Heliu', false, 90),
('Neutron', true, 91), ('Proton', false, 91), ('Electron', false, 91), ('Fotoni', false, 91),
('Hertz', true, 92), ('Joule', false, 92), ('Pascal', false, 92), ('Newton', false, 92),
('Uranus', false, 93), ('Saturn', true, 93), ('Jupiter', false, 93), ('Neptun', false, 93),
('Arhimede', true, 94), ('Galileo Galilei', false, 94), ('Isaac Newton', false, 94), ('Johannes Kepler', false, 94),
('Uraniu', false, 95), ('Plumb', false, 95), ('Osmiu', true, 95), ('Iridiu', false, 95),
('Pancreas', true, 96), ('Ficat', false, 96), ('Splină', false, 96), ('Rinichi', false, 96),
('Antoine Lavoisier', false, 97), ('Marie Curie', true, 97), ('Dmitri Mendeleev', false, 97), ('Robert Hooke', false, 97),
('Presiunea atmosferică', false, 98), ('Temperatura', true, 98), ('Umiditatea', false, 98), ('Viteza vântului', false, 98),
('Au', true, 99), ('Ag', true, 99), ('Fe', false, 99), ('Hg', false, 99),
('Venus', false, 100), ('Mercur', false, 100), ('Marte', false, 100), ('Pământ', true, 100);

INSERT INTO answers (text, is_correct, question_id) VALUES
('Mihai Eminescu', true, 101), ('Liviu Rebreanu', false, 101), ('Marin Preda', false, 101), ('Mihail Sadoveanu', false, 101),
('Mihai Eminescu', true, 102), ('Ion Creangă', false, 102), ('Mircea Eliade', false, 102), ('George Călinescu', false, 102),
('Ioan Slavici', false, 103), ('Liviu Rebreanu', false, 103), ('Ion Creangă', false, 103), ('Mateiu Caragiale', true, 103),
('Ion Creangă', false, 104), ('Marin Preda', false, 104), ('Mihail Sadoveanu', false, 104), ('George Călinescu', true, 104),
('Mihail Sadoveanu', false, 105), ('Camil Petrescu', false, 105), ('Barbu Ștefănescu Delavrancea', false, 105), ('George Coșbuc', true, 105),
('Barbu Ștefănescu Delavrancea', false, 106), ('Marin Preda', false, 106), ('Mircea Eliade', false, 106), ('Liviu Rebreanu', true, 106),
('Camil Petrescu', true, 107), ('Mircea Eliade', false, 107), ('Liviu Rebreanu', false, 107), ('Mateiu Caragiale', false, 107),
('Zaharia Stancu', false, 108), ('Marin Preda', true, 108), ('Mihail Sadoveanu', false, 108), ('Mircea Eliade', false, 108),
('Marin Preda', false, 109), ('Mihail Sadoveanu', false, 109), ('Camil Petrescu', false, 109), ('Mircea Eliade', true, 109),
('Ioan Slavici', true, 110), ('Liviu Rebreanu', false, 110), ('Mihail Sadoveanu', false, 110), ('Ion Creangă', false, 110),
('Mihai Eminescu', false, 111), ('George Bacovia', true, 111), ('Tudor Arghezi', false, 111), ('Nichita Stănescu', false, 111),
('Mihai Eminescu', false, 112), ('Tudor Arghezi', false, 112), ('George Coșbuc', false, 112), ('George Bacovia', true, 112),
('Ioan Slavici', true, 113), ('Mihail Sadoveanu', false, 113), ('Liviu Rebreanu', false, 113), ('Ion Creangă', false, 113),
('Liviu Rebreanu', true, 114), ('Mihail Sadoveanu', false, 114), ('Marin Preda', false, 114), ('Mircea Eliade', false, 114),
('Mateiu Caragiale', true, 115), ('Ion Creangă', false, 115), ('Mihai Eminescu', false, 115), ('Marin Preda', false, 115),
('Mihail Sadoveanu', false, 116), ('Ion Creangă', false, 116), ('George Coșbuc', false, 116), ('Liviu Rebreanu', true, 116),
('Barbu Ștefănescu Delavrancea', false, 117), ('Mihail Sadoveanu', false, 117), ('Liviu Rebreanu', true, 117), ('George Coșbuc', false, 117),
('Ion Creangă', false, 118), ('Mihai Eminescu', false, 118), ('Liviu Rebreanu', false, 118), ('Tudor Arghezi', true, 118),
('Mateiu Caragiale', false, 119), ('Ion Creangă', false, 119), ('Liviu Rebreanu', false, 119), ('George Coșbuc', true, 119),
('Ion Creangă', true, 120), ('Mihail Sadoveanu', false, 120), ('Mateiu Caragiale', false, 120), ('Mihai Eminescu', false, 120);

INSERT INTO answers (text, is_correct, question_id) VALUES
('Pele', true, 121), ('Lionel Messi', false, 121), ('Diego Maradona', false, 121), ('Cristiano Ronaldo', false, 121),
('Judo', false, 122), ('Karate', false, 122), ('Kendo', false, 122), ('Sumo', true, 122),
('1896', true, 123), ('1900', false, 123), ('1888', false, 123), ('1924', false, 123),
('Novak Djokovic', true, 124), ('Roger Federer', false, 124), ('Rafael Nadal', false, 124), ('Pete Sampras', false, 124),
('FC Barcelona', false, 125), ('Bayern München', false, 125), ('AC Milan', false, 125), ('Real Madrid', true, 125),
('Michael Jordan', true, 126), ('LeBron James', false, 126), ('Kobe Bryant', false, 126), ('Shaquille O''Neal', false, 126),
('60 de minute', false, 127), ('90 de minute', true, 127), ('120 de minute', false, 127), ('80 de minute', false, 127),
('Beijing', true, 128), ('Atena', false, 128), ('Londra', false, 128), ('Rio de Janeiro', false, 128),
('Usain Bolt', true, 129), ('Carl Lewis', false, 129), ('Michael Johnson', false, 129), ('Mo Farah', false, 129),
('Franța', true, 130), ('Germania', false, 130), ('Brazilia', false, 130), ('Argentina', false, 130),
('Serena Williams', true, 131), ('Martina Navratilova', false, 131), ('Steffi Graf', false, 131), ('Margaret Court', false, 131),
('Baschet', true, 132), ('Fotbal', false, 132), ('Tenis', false, 132), ('Atletism', false, 132),
('Muhammad Ali', true, 133), ('Mike Tyson', false, 133), ('George Foreman', false, 133), ('Joe Frazier', false, 133),
('3 minute', true, 134), ('5 minute', false, 134), ('10 minute', false, 134), ('12 minute', false, 134),
('Eddy Merckx', true, 135), ('Lance Armstrong', false, 135), ('Fausto Coppi', false, 135), ('Miguel Indurain', false, 135),
('105 m x 68 m', true, 136), ('105 m x 65 m', false, 136), ('100 m x 70 m', false, 136), ('110 m x 75 m', false, 136),
('New England Patriots', true, 137), ('Dallas Cowboys', false, 137), ('Green Bay Packers', false, 137), ('San Francisco 49ers', false, 137),
('Jack Nicklaus', true, 138), ('Arnold Palmer', false, 138), ('Phil Mickelson', false, 138), ('Gary Player', false, 138),
('Italia', true, 139), ('Franța', false, 139), ('Spania', false, 139), ('Germania', false, 139),
('42.195 kilometri', true, 140), ('40 kilometri', false, 140), ('45 kilometri', false, 140), ('50 kilometri', false, 140);

INSERT INTO answers (text, is_correct, question_id) VALUES
('63', false, 141), ('56', true, 141), ('58', false, 141), ('54', false, 141),
('4', true, 142), ('3', false, 142), ('6', false, 142), ('5', false, 142),
('Numărul Pi', true, 143), ('Numărul lui Euler', false, 143), ('Numărul de Aur', false, 143), ('Numărul Avogadro', false, 143),
('1000', false, 144), ('100', false, 144),('500', true, 144), ('50', false, 144),
('180 grade', true, 145), ('270 grade', false, 145), ('90 grade', false, 145), ('360 grade', false, 145),
('5', true, 146), ('-5', false, 146), ('10', false, 146), ('0', false, 146),
('212', true, 147), ('100', false, 147), ('32', false, 147), ('1000', false, 147), 
('Rata de schimb a funcției', true, 148), ('Integrala funcției', false, 148), ('Limitele funcției', false, 148), ('Derivata inversă', false, 148),
('100', false, 149), ('10000', false, 149), ('100000', false, 149), ('1000', true, 149),
('53', true, 150), ('47', false, 150), ('59', false, 150), ('43', false, 150),
('O formulă matematică care leagă lungimea celor trei laturi ale unui triunghi dreptunghic', true, 151), ('O formulă matematică care leagă lungimea celor trei laturi ale unui triunghi echilateral', false, 151), ('O formulă matematică care leagă lungimea celor trei laturi ale unui triunghi isoscel', false, 151), ('O formulă matematică care leagă lungimea unghiurilor unui triunghi oarecare', false, 151),
('5/4', true, 152), ('7/8', false, 152), ('1/2', false, 152), ('3/5', false, 152),
('13', false, 153), ('17', true, 153), ('19', false, 153), ('23', false, 153),
('Teorema care afirmă că într-un triunghi dreptunghic, pătratul lungimii ipotenuzei este egal cu suma pătratelor lungimilor celorlalte două laturi', true, 154), ('O metodă pentru calculul ariei unui cerc', false, 154), ('O regulă pentru calculul volumului unui cub', false, 154), ('O formulă pentru calculul perimetrului unui triunghi echilateral', false, 154),
('1', true, 155), ('2', false, 155), ('3', false, 155), ('4', false, 155);
 
INSERT INTO answers (text, is_correct, question_id) VALUES
('Mona Lisa', true, 141), ('Ultima Cină', false, 141), ('Răstignirea', false, 141), ('Nașterea lui Venus', false, 141),
('Michelangelo Buonarroti', true, 142), ('Gian Lorenzo Bernini', false, 142), ('Donatello', false, 142), ('Auguste Rodin', false, 142),
('Renaștere', true, 143), ('Rococo', false, 143), ('Romantism', false, 143), ('Baroc', false, 143),
('Noaptea stelată', true, 144), ('Lumina Lunii', false, 144), ('Amurgul', false, 144), ('Punctul stelar', false, 144),
('Vincent van Gogh', true, 145), ('Claude Monet', false, 145), ('Pablo Picasso', false, 145), ('Leonardo da Vinci', false, 145),
('Impresionism', true, 146), ('Expresionism', false, 146), ('Cubism', false, 146), ('Suprarealism', false, 146),
('Leonardo da Vinci', true, 147), ('Donatello', false, 147), ('Raphael', false, 147), ('Michelangelo Buonarroti', false, 147),
('Ludwig van Beethoven', true, 148), ('Franz Schubert', false, 148), ('Wolfgang Amadeus Mozart', false, 148), ('Johann Sebastian Bach', false, 148),
('Alexander Calder', true, 149), ('Pablo Picasso', false, 149), ('Henry Moore', false, 149), ('Constantin Brâncuși', false, 149),
('Jérôme Bosch', true, 150), ('Claude Monet', false, 150), ('Edvard Munch', false, 150), ('Vincent van Gogh', false, 150),
('Sandro Botticelli', true, 151), ('Leonardo da Vinci', false, 151), ('Rafael', false, 151), ('Caravaggio', false, 151),
('Salvador Dalí', true, 152), ('Pablo Picasso', false, 152), ('Vincent van Gogh', false, 152), ('Claude Monet', false, 152),
('Salvador Dalí', true, 153), ('Vincent van Gogh', false, 153), ('Pablo Picasso', false, 153), ('Leonardo da Vinci', false, 153),
('Michelangelo Buonarroti', true, 154), ('Donatello', false, 154), ('Gian Lorenzo Bernini', false, 154), ('Leonardo da Vinci', false, 154),
('Pablo Picasso', true, 155), ('Claude Monet', false, 155), ('Vincent van Gogh', false, 155), ('Leonardo da Vinci', false, 155),
('Baroc', true, 156), ('Renascentist', false, 156), ('Impresionist', false, 156), ('Modernist', false, 156),
('Edvard Munch', true, 157), ('Leonardo da Vinci', false, 157), ('Pablo Picasso', false, 157), ('Claude Monet', false, 157),
('Auguste Rodin', true, 158), ('Michelangelo Buonarroti', false, 158), ('Gian Lorenzo Bernini', false, 158), ('Donatello', false, 158),
('Édouard Manet', true, 159), ('Claude Monet', false, 159), ('Vincent van Gogh', false, 159), ('Pablo Picasso', false, 159),
('Auguste Rodin', true, 160), ('Michelangelo Buonarroti', false, 160), ('Pablo Picasso', false, 160), ('Vincent van Gogh', false, 160);

INSERT INTO answers (text, is_correct, question_id) VALUES
('Mona Lisa', true, 156), ('Ultima Cină', false, 156), ('Răstignirea', false, 156), ('Nașterea lui Venus', false, 156),
('Michelangelo Buonarroti', true, 157), ('Gian Lorenzo Bernini', false, 157), ('Donatello', false, 157), ('Auguste Rodin', false, 157),
('Renaștere', true, 158), ('Rococo', false, 158), ('Romantism', false, 158), ('Baroc', false, 158),
('Noaptea stelată', true, 159), ('Lumina Lunii', false, 159), ('Amurgul', false, 159), ('Punctul stelar', false, 159),
('Vincent van Gogh', true, 160), ('Claude Monet', false, 160), ('Pablo Picasso', false, 160), ('Leonardo da Vinci', false, 160),
('Impresionism', true, 161), ('Expresionism', false, 161), ('Cubism', false, 161), ('Suprarealism', false, 161),
('Leonardo da Vinci', true, 162), ('Donatello', false, 162), ('Raphael', false, 162), ('Michelangelo Buonarroti', false, 162),
('Ludwig van Beethoven', true, 163), ('Franz Schubert', false, 163), ('Wolfgang Amadeus Mozart', false, 163), ('Johann Sebastian Bach', false, 163),
('Alexander Calder', true, 164), ('Pablo Picasso', false, 164), ('Henry Moore', false, 164), ('Constantin Brâncuși', false, 164),
('Jérôme Bosch', true, 165), ('Claude Monet', false, 165), ('Edvard Munch', false, 165), ('Vincent van Gogh', false, 165),
('Sandro Botticelli', true, 166), ('Leonardo da Vinci', false, 166), ('Rafael', false, 166), ('Caravaggio', false, 166),
('Salvador Dalí', true, 167), ('Pablo Picasso', false, 167), ('Vincent van Gogh', false, 167), ('Claude Monet', false, 167),
('Salvador Dalí', true, 168), ('Vincent van Gogh', false, 168), ('Pablo Picasso', false, 168), ('Leonardo da Vinci', false, 168),
('Michelangelo Buonarroti', true, 169), ('Donatello', false, 169), ('Gian Lorenzo Bernini', false, 169), ('Leonardo da Vinci', false, 169),
('Pablo Picasso', true, 170), ('Claude Monet', false, 170), ('Vincent van Gogh', false, 170), ('Leonardo da Vinci', false, 170),
('Baroc', true, 171), ('Renascentist', false, 171), ('Impresionist', false, 171), ('Modernist', false, 171),
('Edvard Munch', true, 172), ('Leonardo da Vinci', false, 172), ('Pablo Picasso', false, 172), ('Claude Monet', false, 172),
('Auguste Rodin', true, 173), ('Michelangelo Buonarroti', false, 173), ('Gian Lorenzo Bernini', false, 173), ('Donatello', false, 173),
('Édouard Manet', true, 174), ('Claude Monet', false, 174), ('Vincent van Gogh', false, 174), ('Pablo Picasso', false, 174),
('Auguste Rodin', true, 175), ('Michelangelo Buonarroti', false, 175), ('Pablo Picasso', false, 175), ('Vincent van Gogh', false, 175);

INSERT INTO answers (text, is_correct, question_id) VALUES
('Led Zeppelin', false, 176), ('Pink Floyd', true, 176), ('The Beatles', false, 176), ('Queen', false, 176),
('Wolfgang Amadeus Mozart', false, 177), ('Johann Sebastian Bach', false, 177), ('Frédéric Chopin', false, 177), ('Ludwig van Beethoven', true, 177),
('Orga', true, 178), ('Vioara', false, 178), ('Trompeta', false, 178), ('Pianul', false, 178),
('Prince', false, 179), ('Madonna', false, 179), ('Elvis Presley', false, 179), ('Michael Jackson', true, 179),
('Giuseppe Verdi', true, 180), ('Wolfgang Amadeus Mozart', false, 180), ('Ludwig van Beethoven', false, 180), ('Johann Sebastian Bach', false, 180),
('Led Zeppelin', false, 181), ('Queen', false, 181), ('The Beatles', true, 181), ('Pink Floyd', false, 181),
('Michael Jackson', true, 182), ('Elvis Presley', false, 182), ('Madonna', false, 182), ('Prince', false, 182),
('Pianul', false, 183), ('Chitara', false, 183), ('Vioara', true, 183), ('Flautul', false, 183),
('Wolfgang Amadeus Mozart', false, 184), ('Ludwig van Beethoven', true, 184), ('Johann Sebastian Bach', false, 184), ('Frédéric Chopin', false, 184),
('Wolfgang Amadeus Mozart', false, 185), ('Johann Sebastian Bach', false, 185), ('Frédéric Chopin', false, 185), ('Ludwig van Beethoven', true, 185),
('Chitara', true, 186), ('Pianul', false, 186), ('Bateriile', false, 186), ('Vioara', false, 186),
('Johann Sebastian Bach', false, 187), ('Ludwig van Beethoven', false, 187), ('Johann Pachelbel', true, 187), ('Wolfgang Amadeus Mozart', false, 187),
('Elvis Presley', true, 188), ('Michael Jackson', false, 188), ('Madonna', false, 188), ('Prince', false, 188),
('Michael Jackson', false, 189), ('Madonna', false, 189), ('Prince', true, 189), ('Elvis Presley', false, 189),
('Georges Bizet', true, 190), ('Giuseppe Verdi', false, 190), ('Wolfgang Amadeus Mozart', false, 190), ('Ludwig van Beethoven', false, 190),
('Led Zeppelin', false, 191), ('Queen', false, 191), ('The Beatles', true, 191), ('Pink Floyd', false, 191),
('Orchestra Filarmonică din Viena', true, 192), ('Orchestra Filarmonică din Berlin', false, 192), ('Orchestra Simfonică din Londra', false, 192), ('Orchestra Simfonică din New York', false, 192),
('Michael Jackson', false, 193), ('Madonna', false, 193), ('Prince', false, 193), ('Elvis Presley', true, 193),
('Cole Porter', false, 194), ('George Gershwin', true, 194), ('Leonard Bernstein', false, 194), ('Irving Berlin', false, 194),
('Madonna', true, 195), ('Lady Gaga', false, 195), ('Britney Spears', false, 195), ('Rihanna', false, 195);


INSERT INTO answers (text, is_correct, question_id) VALUES
('Nikola Tesla', false, 196), ('Alexander Graham Bell', false, 196), ('Henry Ford', false, 196), ('Thomas Edison', true, 196),
('Thomas Edison', false, 197), ('Nikola Tesla', false, 197), ('Alexander Graham Bell', true, 197), ('Guglielmo Marconi', false, 197),
('Wernher von Braun', true, 198), ('Elon Musk', false, 198), ('Isaac Newton', false, 198), ('Galileo Galilei', false, 198),
('Henry Ford', false, 199), ('Gottlieb Daimler', false, 199), ('Karl Benz', true, 199), ('Nikolaus Otto', false, 199),
('Johannes Gutenberg', true, 200), ('Thomas Edison', false, 200), ('Alexander Graham Bell', false, 200), ('Nikola Tesla', false, 200),
('Gustave Eiffel', false, 201), ('Samuel Morse', false, 201), ('Orville și Wilbur Wright', true, 201), ('Leonardo da Vinci', false, 201),
('Nikola Tesla', false, 202), ('Guglielmo Marconi', true, 202), ('Alexander Graham Bell', false, 202), ('Thomas Edison', false, 202),
('Steve Jobs și Steve Wozniak', true, 203), ('Bill Gates', false, 203), ('Tim Berners-Lee', false, 203), ('Alan Turing', false, 203),
('Nikola Tesla', false, 204), ('Thomas Edison', true, 204), ('Alexander Graham Bell', false, 204), ('Henry Ford', false, 204),
('Hans Lippershey', true, 205), ('Galileo Galilei', false, 205), ('Johannes Kepler', false, 205), ('Isaac Newton', false, 205),
('Nikola Tesla', false, 206), ('Alexander Graham Bell', false, 206), ('Guglielmo Marconi', true, 206), ('Thomas Edison', false, 206),
('Elias Howe', true, 207), ('Thomas Edison', false, 207), ('Alexander Graham Bell', false, 207), ('Nikola Tesla', false, 207),
('Louis Pasteur', false, 208), ('Alexander Fleming', true, 208), ('Marie Curie', false, 208), ('Albert Einstein', false, 208),
('Thomas Edison', false, 209), ('Alexander Graham Bell', false, 209), ('George Eastman', true, 209), ('Nikola Tesla', false, 209),
('Nikolaus Otto', false, 210), ('Karl Benz', false, 210), ('James Watt', true, 210), ('Henry Ford', false, 210);

SELECT pg_get_serial_sequence('answers', 'answer_id');
ALTER SEQUENCE public.answers_answer_id_seq RESTART WITH 1;
delete from answers where true;
select * from answers;


