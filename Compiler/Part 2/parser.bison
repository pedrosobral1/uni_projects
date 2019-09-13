// Tokens
%token
  INTEIRO
  EQUALVAR
  PRINT
  SCAN
  IF
  ELSE
  WHILE
  END
  VAR
  STR
  INT
  PLUS
  MULT
  MINUS
  DIV
  EQUAL
  DIFF
  LT
  GT
  LTEQ
  GTEQ
  AND
  OR

// Operator associativity & precedence
%left PLUS MINUS
%left MULT

// Root-level grammar symbol
%start program;

// Types/values in association to grammar symbols.
%union {
  int intValue;
  char* varName;
  char* stringValue;
  Instr* instruction;
  ListInstr* instrlist;
}

%type <intValue> INT;
%type <varName> VAR;
%type <stringValue> STR
%type <instruction> instr;
%type <instruction> expr;
%type <instrlist> list;
%type <instrlist> exprlist;


// Use "%code requires" to make declarations go
// into both parser.c and parser.h
%code requires {
#include <stdio.h>
#include <stdlib.h>
#include "stack.h"

extern int yylex();
extern int yyline;
extern char* yytext;
extern FILE* yyin;
extern void yyerror(const char* msg);
ListInstr* root;
}

%%
program: list { root = $1; }

list:
  instr {
    $$ = addnode($1,NULL);
  }
  ;
  |
  instr list {
    $$ = addnode($1,$2);
  }
  ;

exprlist:
  expr {
    $$ = addnode($1,NULL);
  }
  ;

instr:
  INTEIRO VAR {
    $$ = stack_var_decl($2);
  }
  ;
  |
  VAR EQUALVAR exprlist {
    $$ = stack_var_attr($1,$3);
  }
  ;
  |
  VAR EQUALVAR VAR {
    $$ = stack_var_attr_2($1,$3);
  }
  ;
  |
  INTEIRO VAR EQUALVAR exprlist {
    $$ = stack_var_init($2,$4);
  }
  ;
  |
  INTEIRO VAR EQUALVAR VAR {
    $$ = stack_var_init_2($2,$4);
  }
  ;
  |
  SCAN VAR {
    $$ = stack_scan($2);
  }
  ;
  |
  PRINT VAR {
    $$ = stack_print_var($2);
  }
  ;
  |
  PRINT exprlist {
    $$ = stack_print($2);
  }
  ;
  |
  IF exprlist list END {
    $$ = stack_if_statem($2,$3);
  }
  ;
  |
  IF exprlist list ELSE list END {
    $$ = stack_if_else($2,$3,$5);
  }
  ;
  |
  WHILE exprlist list END {
    $$ = stack_while($2,$3);
  }
  ;


expr:
  INT {
    $$ = stack_ldc($1);
  }
  ;
  |
  PLUS exprlist exprlist {
    $$ = stack_operation(PLUS,$2,$3);
  }
  ;
  |
  MULT exprlist exprlist {
    $$ = stack_operation(MULT,$2,$3);
  }
  ;
  |
  MINUS exprlist exprlist {
    $$ = stack_operation(MINUS,$2,$3);
  }
  ;
  |
  DIV exprlist exprlist {
    $$ = stack_operation(DIV,$2,$3);
  }
  ;
  |
  EQUAL exprlist exprlist {
    $$ = stack_operation_boolean(EQUAL,$2,$3);
  }
  ;
  |
  DIFF exprlist exprlist {
    $$ = stack_operation_boolean(DIFF,$2,$3);
  }
  ;
  |
  LT exprlist exprlist {
    $$ = stack_operation_boolean(LT,$2,$3);
  }
  ;
  |
  GT exprlist exprlist {
    $$ = stack_operation_boolean(GT,$2,$3);
  }
  ;
  |
  LTEQ exprlist exprlist {
    $$ = stack_operation_boolean(LTEQ,$2,$3);
  }
  ;
  |
  GTEQ exprlist exprlist {
    $$ = stack_operation_boolean(GTEQ,$2,$3);
  }
  ;
  |
  AND exprlist exprlist {
    $$ = stack_operation_boolean(AND,$2,$3);
  }
  ;
  |
  OR exprlist exprlist {
    $$ = stack_operation_boolean(OR,$2,$3);
  }
  ;


%%

void yyerror(const char* err) {
  printf("Line %d: %s - '%s'\n", yyline, err, yytext  );
}
