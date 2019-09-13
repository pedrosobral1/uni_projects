// Tokens
%token
  BRLEFT
  BRRIGHT
  PLEFT
  PRIGHT
  INTEIRO
  MAIN
  IF
  ELSE
  WHILE
  SC
  CM
  STR
  PRINT
  SCAN
  POINTER
  VAR
  EQUALVAR
  INT
  PLUS
  MINUS
  MULT
  DIV
  MOD
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
%left DIV MULT
%left MOD

// Root-level grammar symbol
%start program;

// Types/values in association to grammar symbols.
%union {
  int intValue;
  char* stringValue;
  Expr* exprValue;
  char* varName;
  Cmd* command;
  Cmdlist* commandlist;

}

%type <intValue> INT
%type <exprValue> expr
%type <varName> VAR
%type <command> cmd
%type <commandlist> cmdlist
%type <stringValue> STR


// Use "%code requires" to make declarations go
// into both parser.c and parser.h
%code requires {
#include <stdio.h>
#include <stdlib.h>
#include "ast.h"

extern int yylex();
extern int yyline;
extern char* yytext;
extern FILE* yyin;
extern void yyerror(const char* msg);
Cmdlist* root;
}

%%
program: INTEIRO MAIN PLEFT PRIGHT BRLEFT cmdlist BRRIGHT { root = $6; }


cmdlist:
  cmd {
    $$ = ast_cmd_list($1,NULL);
  }
  ;
  |
  cmd cmdlist {
    $$ = ast_cmd_list($1,$2);
  }
  ;


cmd:

  INTEIRO VAR SC {
    $$ = ast_var_decl($2);
  }
  ;
  |
  VAR EQUALVAR expr SC {
    $$ = ast_var_attr($1,$3);
  }
  ;
  |
  VAR EQUALVAR VAR SC {
    $$ = ast_var_attr_2($1,$3);
  }
  ;
  |
  INTEIRO VAR EQUALVAR expr SC {
    $$ = ast_var_init($2,$4);
  }
  ;
  |
  INTEIRO VAR EQUALVAR VAR SC {
    $$ = ast_var_init_2($2,$4);
  }
  ;
  |
  SCAN PLEFT STR CM POINTER VAR PRIGHT SC {
    $$ = ast_scan($6);
  }
  ;
  |
  PRINT PLEFT STR PRIGHT SC { /*print de string*/
    $$ = ast_print($3,NULL);
  }
  ;
  |
  PRINT PLEFT STR CM expr PRIGHT SC {
    $$ = ast_print($3,$5);
  }
  ;
  |
  PRINT PLEFT STR CM VAR PRIGHT SC {
    $$ = ast_print_var($5);
  }
  ;
  |
  IF PLEFT expr PRIGHT BRLEFT cmdlist BRRIGHT { /*IF*/
    $$ = ast_if_statem($3,$6);
  }
  ;
  |
  IF PLEFT expr PRIGHT BRLEFT cmdlist BRRIGHT ELSE BRLEFT cmdlist BRRIGHT { /*IF ELSE*/
    $$ = ast_if_else($3,$6,$10);
  }
  ;
  |
  WHILE PLEFT expr PRIGHT BRLEFT cmdlist BRRIGHT { /*IF*/
    $$ = ast_while_statem($3,$6);
  }
  ;


expr:
  INT {
    $$ = ast_integer($1);
  }
  ;
  |
  expr PLUS expr {
    $$ = ast_operation(PLUS, $1, $3);
  }
  ;
  |
  expr MINUS expr {
  $$ = ast_operation(MINUS, $1, $3);
  }
  ;
  |
  expr MULT expr {
  $$ = ast_operation(MULT, $1, $3);
  }
  ;
  |
  expr DIV expr{
  $$ = ast_operation(DIV, $1, $3);
  }
  ;
  |
  expr MOD expr {
  $$ = ast_operation(MOD, $1, $3);
  }
  ;
  |
  PLEFT INT PRIGHT {
    $$ = ast_integer($2);
  }
  ;
  |
  PLEFT expr PLUS expr PRIGHT {
    $$ = ast_operation(PLUS, $2, $4);
  }
  ;
  |
  PLEFT expr MINUS expr PRIGHT {
  $$ = ast_operation(MINUS, $2, $4);
  }
  ;
  |
  PLEFT expr MULT expr PRIGHT {
  $$ = ast_operation(MULT, $2, $4);
  }
  ;
  |
  PLEFT expr DIV expr PRIGHT {
  $$ = ast_operation(DIV, $2, $4);
  }
  ;
  |
  PLEFT expr MOD expr PRIGHT {
  $$ = ast_operation(MOD, $2, $4);
  }
  ;
  |
  expr EQUAL expr {
  $$ = ast_operation_boolean(EQUAL,$1, $3);
  }
  ;
  |
  expr DIFF expr {
  $$ = ast_operation_boolean(DIFF,$1, $3);
  }
  ;
  |
  expr LT expr {
  $$ = ast_operation_boolean(LT,$1, $3);
  }
  ;
  |
  expr GT expr {
  $$ = ast_operation_boolean(GT,$1, $3);
  }
  ;
  |
  expr LTEQ expr {
  $$ = ast_operation_boolean(LTEQ,$1, $3);
  }
  ;
  |
  expr GTEQ expr {
  $$ = ast_operation_boolean(GTEQ,$1,$3);
  }
  ;
  |
  expr AND expr {
  $$ = ast_operation_boolean(AND,$1,$3);
  }
  ;
  |
  expr OR expr {
  $$ = ast_operation_boolean(OR,$1,$3);
  }
  ;
  |
  PLEFT expr EQUAL expr PRIGHT {
  $$ = ast_operation_boolean(EQUAL, $2, $4);
  }
  ;
  |
  PLEFT expr DIFF expr PRIGHT {
  $$ = ast_operation_boolean(DIFF, $2, $4);
  }
  ;
  |
  PLEFT expr LT expr PRIGHT {
  $$ = ast_operation_boolean(LT, $2, $4);
  }
  ;
  |
  PLEFT expr GT expr PRIGHT {
  $$ = ast_operation_boolean(GT, $2, $4);
  }
  ;
  |
  PLEFT expr LTEQ expr PRIGHT {
  $$ = ast_operation_boolean(LTEQ, $2, $4);
  }
  ;
  |
  PLEFT expr GTEQ expr PRIGHT {
  $$ = ast_operation_boolean(GTEQ, $2, $4);
  }
  ;


%%

void yyerror(const char* err) {
  printf("Line %d: %s - '%s'\n", yyline, err, yytext  );
}
