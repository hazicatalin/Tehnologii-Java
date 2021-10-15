Pagina web:
-doua casute pentru adaugare text si numar
-doua checkbox-uri pentru variabilele booleene
-buton pentru activarea servletului
Servlet:
-citesc datele introduse in pagina web
-citesc parametrii request-ului si ii afisez in log
-daca mock este true afisez in pagina web "confirm"
-daca mock este fals, atunci verific sync-ul
-daca sync este fals, atunci pur si simplu scriu in fisier linia, altfel scriu synchronized in acesta
-dupa scrierea in fisier ciresc toate liniile, iau primuele elemente pana la virgula, si ordonez dupa acestea array-ul.
-afisez array ul pe pagina web
