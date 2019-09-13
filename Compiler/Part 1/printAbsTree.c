#include <stdio.h>
#include <stdbool.h>
#include "parser.h"

void printExpr(Expr* expr,int count) {
  if (expr == 0) {
    yyerror("Null expression!!");
  }
  else if (expr->kind == E_INTEGER) { //inteiro
    printf("%*c",count,' ');
    printf("%d",expr->attr.value);
    printf("\n");
  }
  else if (expr->kind == E_OPERATION) { //op inteiro
    printf("%*c",count,' ');
    switch (expr->attr.op.operator) {
      case PLUS:
        printf("+");
        break;
      case MINUS:
        printf("-");
        break;
      case MULT:
        printf("*");
        break;
      case DIV:
        printf("/");
        break;
      case MOD:
        printf("%%");
        break;
      default: yyerror("Unknown operator!");
    }
    printf("\n");
    printExpr(expr->attr.op.left,count+3);
    printExpr(expr->attr.op.right,count+3);
  }
  else if (expr->kind == E_BOOLEAN) { //op booleana
    printf("%*c",count,' ');
    switch (expr->attr.op.operator) {
      case EQUAL:
        printf("==");
        break;
      case DIFF:
        printf("!=");
        break;
      case LT:
        printf("<");
        break;
      case GT:
        printf(">");
        break;
      case LTEQ:
        printf("<=");
        break;
      case GTEQ:
        printf(">=");
        break;
      case AND:
        printf("&&");
        break;
      case OR:
        printf("||");
        break;
    }
    printf("\n");
    printExpr(expr->attr.op.left,count+3);
    printExpr(expr->attr.op.right,count+3);
  }
}


void printCmd(Cmd* command,int count){
  if (command== 0) {
    yyerror("Null command!!");
  }
  else if(command->kind == C_DECL){
    printf("%*c",count,' ');
    printf("INT:\n");
    printf("%*c",count+3,' ');
    printf("%s\n", command->attr.attrvar.varName);
  }
  else if(command->kind == C_ATTR){
    printf("%*c",count,' ');
    printf("%s\n", command->attr.attrvar.varName);
    printf("%*c",count,' ');
    printf(":=\n");
    printExpr(command->attr.attrvar.expr,count+1);
    printf("\n");
  }
  else if(command->kind == C_ATTR_2){
    printf("%*c",count,' ');
    printf("%s\n", command->attr.attrvar_2.varName0);
    printf("%*c",count+3,' ');
    printf(":=\n");
    printf("%*c",count+4,' ');
    printf("%s\n", command->attr.attrvar_2.varName1);
    printf("\n");
  }
  else if(command->kind == C_INIT){
    printf("%*c",count,' ');
    printf("INT:\n");
    printf("%*c",count+3,' ');
    printf("%s\n", command->attr.init.varName);
    printf("%*c",count+3 ,' ');
    printf(":=\n");
    printExpr(command->attr.init.expr,count+4);
    printf("\n");
  }
  else if(command->kind == C_INIT_2){
    printf("%*c",count,' ');
    printf("INT:\n");
    printf("%*c",count+3,' ');
    printf("%s\n", command->attr.init_2.varName0);
    printf("%*c",count+3 ,' ');
    printf(":=\n");
    printf("%*c",count+4,' ');
    printf("%s\n", command->attr.init_2.varName1);
    printf("\n");
  }
  else if(command->kind == C_SCAN){
    printf("%*c",count,' ');
    printf("SCANF:\n");
    printf("%*c",count+5,' ');
    printf("%s\n", command->attr.scan.varName);
  }
  else if(command->kind == C_PRINT){
    printf("%*c",count,' ');
    printf("PRINTF:\n");
    if (command->attr.print.expr == NULL){
      printf("%*c",count+5,' ');
      printf("%s\n", command->attr.print.str);
      printf("\n");
    }
    else {
      printExpr(command->attr.print.expr,count+6);
      printf("\n");
    }
  }
  else if(command->kind == C_PRINT_VAR){
    printf("%*c",count,' ');
    printf("PRINTF:\n");
    printf("%*c",count+5,' ');
    printf(" %s\n", command->attr.print_var.varName);
    printf("\n");
  }
  else if(command->kind == C_IF){
    printf("%*c",count,' ');
    printf("IF:\n");
    printExpr(command->attr.if_statem.expr,count+3);
    Cmdlist* then = command->attr.if_statem.cmdlist;
    while (then != NULL){
      printCmd(then->command,count+6);
      then = then->next;
    }
    printf("%*c",count,' ');
    printf("END\n");
  }
  else if(command->kind == C_IF_ELSE){
    printf("%*c",count,' ');
    printf("IF:\n");
    printExpr(command->attr.if_else.expr,count+3);
    Cmdlist* cmd_if = command->attr.if_else.cmdlist_if;
    while (cmd_if != NULL){
      printCmd(cmd_if->command,count+6);
      cmd_if = cmd_if->next;
    }
    printf("%*c",count,' ');
    printf("ELSE:\n");
    Cmdlist* cmd_else = command->attr.if_else.cmdlist_else;
    while (cmd_else != NULL){
      printCmd(cmd_else->command,count+6);
      cmd_else = cmd_else->next;
    }
    printf("%*c",count,' ');
    printf("END\n");
  }
  else if(command->kind == C_WHILE){
    printf("%*c",count,' ');
    printf("WHILE:\n");
    printExpr(command->attr.while_statem.expr,count+3);
    Cmdlist* cmdlist = command->attr.while_statem.cmdlist;
    while (cmdlist!= NULL){
      printCmd(cmdlist->command,count+6);
      cmdlist = cmdlist->next;
    }
    printf("%*c",count,' ');
    printf("END\n");
  }
}

int main(int argc, char** argv) {
  --argc; ++argv;
  if (argc != 0) {
    yyin = fopen(*argv, "r");
    if (!yyin) {
      printf("'%s': could not open file\n", *argv);
      return 1;
    }
  } //  yyin = stdin
  if (yyparse() == 0) {
    Cmdlist* node = root;
    while (node!=NULL){
      printCmd(node->command,0);
      printf("\n");
      node=node->next;
    }
  }
  return 0;


}
