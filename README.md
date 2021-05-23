# Clinic Management

O aplicatie minimala pentru gestionarea activitatii unui cabinet medical.

Proiectul are scop didactic.

## Cateva dintre activitati

1. Afisarea tuturor angajatilor, cu toate informatiile aferente
2. Aflarea istoricului medical al unui pacient (diagnostice, prescrieri)
3. Adaugarea si modificarea unui diagnostic pentru un pacient
4. Adaugarea medicamentelor pe o reteta medicala
5. Afisarea stocurilor de medicamente si a echipamentelor din cadrul cabinetului medical
6. Plata catre cabinetul medical a unei sume de catre pacient
7. Plata salariilor angajatilor de catre administrator
8. Marirea salariilor angajatilor de catre administrator
9. Adaugarea / anularea unei programari
10. Aflarea, pentru o luna specificata, a sumei platite pentru intretinerea cabinetului
11. Aflarea, pentru o luna specificata, a numarului de pacienti, respectiv a numarului de vizite a pacientilor pe specializare
12. Aflarea, pentru o luna specificata, a sumei incasate de catre cabinet
13. Aflarea profitului pe o luna
14. Aflarea profitului mediu pe pacient pe o luna specificata

## Cateva obiecte posibile
1. Doctor
2. Asistenta
3. Receptionist
4. Pacient
5. Medicament
6. Diagnostic
7. Programare
8. Reteta Medicala

## Baza de date

Pentru a putea utiliza baza de date folosind JDBC,
in cadrul proiectului trebuie adaugat un driver care transpune comenzile
din aplicatie in limbajul sistemului de gestiune a bazei de date (MySQL).

In procesul de development al aplicatiei, pentru a putea folosi MySQL,
a fost necesara instalarea serverului Xampp si rularea MySQL cu ajutorul lui.
De asemenea, pentru interactiunea cu baza de date, folosim MySQL Workbench.

In MySQL Workbench, am creat o conexiune noua si o baza de date in cadrul acesteia.
Am creat un user nou, caruia am acordat toate privilegiile (de administrator).

Pentru a putea realiza conexiunea dintre aplicatia Java si baza de date,
trebuie adaugata o dependinta in proiect, care sa importe driver-ul necesar
de MySQL instalat local. Driver-ul poate fi descarcat de
[aici](https://search.maven.org/classic/remotecontent?filepath=mysql/mysql-connector-java/8.0.20/mysql-connector-java-8.0.20.jar).
