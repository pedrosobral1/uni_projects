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

  /* generic C components */
"{" {return BRLEFT; }
"}" {return BRRIGHT; }
"(" {return PLEFT; }
")" {return PRIGHT; }
"int" {return INTEIRO; } /*usado para atrib de var*/
"main" {return MAIN; }
";" {return SC;}
"," {return CM;}
"=" {return EQUALVAR;}
"printf" {return PRINT;}
"scanf" {return SCAN;}
"&" {return POINTER;}
"if" {return IF;}
"else" {return ELSE;}
"while" {return WHILE;}

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
"/" { return DIV; }
"%" { return MOD;}

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
