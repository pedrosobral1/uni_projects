%{
// HEADERS
#include <stdlib.h>
#include "parser.h"

// variables maintained by the lexical analyser
int yyline = 1;
%}

%option noyywrap

%% /*scan tokens*/
[ \t]+ {  }
#.*\n { yyline++; } /* comentarios */
\n { yyline++; } /*linhas vazias */

"INT:" {return INTEIRO; } /*usado para init de var*/
":=" {return EQUALVAR;}
"PRINTF:" {return PRINT;}
"SCANF:" {return SCAN;}
"IF:" {return IF;}
"ELSE:" {return ELSE;}
"WHILE:" {return WHILE;}
"END" {return END;}

[a-zA-Z][a-zA-Z0-9]* {
   yylval.varName = strdup(yytext);
   return VAR;
 } /*variaveis*/

\"(\\.|[^\\"])*\" {
   yylval.stringValue = strdup(yytext);
   return STR;
} /*string*/

\-?[0-9]+ {
   yylval.intValue = atoi(yytext);
   return INT;
 } /*inteiros*/

 /*aritmetica*/
"+" { return PLUS; }
"-" { return MINUS; }
"*" { return MULT; }
"/" {return DIV; }

 /*boolean*/
"==" {return EQUAL; }
"!=" {return DIFF; }
"<" {return LT; }
">" {return GT; }
"<=" {return LTEQ; }
">=" {return GTEQ; }
"&&" {return AND; }
"||" {return OR; }

.  { yyerror("unexpected character"); }
%%
