#include <stdio.h>
#include <stdbool.h>
#include "parser.h"
#include <string.h>
#include <errno.h>




//////////////              Struct VarList        //////////////
//////////////             Store Variables        //////////////
//generating labels for PCODE
int lab_index=0;
struct _VarList {
  char *varName;
  struct _VarList *next;
};
typedef struct _VarList VarList;

//init VarList
VarList* var_list = NULL;

//store Var in VarList
VarList* putVAR(char *var, VarList* list0){
  if (*var!='x' && *var!='y' && *var!='z'){
    printf("Variable '%c' not accepted!\n\n\n",*var);
    exit(-1);
  }
  VarList* node = (VarList*) malloc(sizeof(VarList));
  node->varName = var;
  node->next = list0;
  return node;
}

//check Var existence in VarList
int checkVAR(char *var){
  VarList* cur = var_list;
  while (cur!=NULL){
    char *cur_var = cur->varName;
    if (*var == *cur_var){
      return 1;
    }
    cur=cur->next;
  }
  return 0;
}
//////////////                 END                //////////////







//////////////                 BEGIN              //////////////
//////////////                 PCODE              //////////////
//print Instr with "expr type"
void printExpr(ListInstr* expr) {

  if (expr == 0) {
    yyerror("Null expression!!");
  }
  else {
    Instr* instr = expr->instr;
    if (instr->kind == I_NUM) {
      printf("ldc %d\n",instr->attr.value);
    }
    else {
      ListInstr* expr_left = instr->attr.op.expr_left;
      ListInstr* expr_right = instr->attr.op.expr_right;
      printExpr(expr_left);
      printExpr(expr_right);
      switch (instr->attr.op.operator) {
        case PLUS:
          printf("adi\n");
          break;
        case MULT:
          printf("mpi\n");
          break;
        case MINUS:
          printf("sbi\n");
          break;
        case EQUAL:
          printf("equ\n");
          break;
        case DIFF:
          printf("neq\n");
          break;
        case LT:
          printf("lt\n");
          break;
        case GT:
          printf("gt\n");
          break;
        case LTEQ:
          printf("let\n");
          break;
        case GTEQ:
          printf("get\n");
          break;
        case AND:
          printf("and\n");
          break;
        case OR:
          printf("or\n");
          break;
      }
    }
  }
}
//print other Instr
void p_code(ListInstr* root){
  Instr* instr = root->instr;

  if (instr->kind == I_DECL){
    char *var = instr->attr.decl.varName;
    if (checkVAR(var)==0){
      var_list = putVAR(var,var_list);
    }
  }
  else if (instr->kind == I_ATTR){
    char *var = instr->attr.attrvar.varName;
    if (checkVAR(var)==0){
      printf("Variable '%c' not declared!\n\n\n",*var);
      exit(-1);
    }
    ListInstr* expr = instr->attr.attrvar.expr;
    printf("lda %c\n", *var);
    printExpr(expr);
    printf("sto\n");
  }
  else if(instr->kind == I_ATTR_2){
    char *var = instr->attr.attrvar_2.varName1;
    if (checkVAR(var)==0){
      printf("Variable '%c' not declared!\n\n\n",*var);
      exit(-1);
    }
    printf("lda %s\n", instr->attr.attrvar_2.varName0);
    printf("lod %c\n", *var);
    printf("sto\n");
  }
  if(instr->kind == I_INIT){
    char *var = instr->attr.init.varName;
    if (checkVAR(var)==0){
      var_list = putVAR(var,var_list);
    }
    ListInstr* expr = instr->attr.init.list_expr;
    printf("lda %c\n", *var);
    printExpr(expr);
    printf("sto\n");
  }
  else if(instr->kind == I_INIT_2){
    char *var = instr->attr.init_2.varName0;
    if (checkVAR(var)==0){
      var_list = putVAR(var,var_list);
    }
    printf("lda %c\n", *var);
    printf("lod %s\n", instr->attr.init_2.varName1);
    printf("sto\n");
  }
  else if(instr->kind == I_SCAN){
    char *var = instr->attr.scan.varName;
    if (checkVAR(var)==0){
      printf("Variable '%c' not declared!\n\n\n",*var);
      exit(-1);
    }
    printf("lda %c\n", *var);
    printf("rdi\n");
  }
  else if(instr->kind == I_PRINT){
    ListInstr* expr = instr->attr.print.list_expr;
    printExpr(expr);
    printf("wri\n");
  }
  else if(instr->kind == I_PRINT_VAR){
    char *var = instr->attr.print_var.varName;
    if (checkVAR(var)==0){
      printf("Variable '%c' not declared!\n\n\n",*var);
      exit(-1);
    }
    printf("lod %c\n", *var);
    printf("wri\n");
  }
  else if(instr->kind == I_IF){
    ListInstr* expr = instr->attr.if_statem.expr;
    ListInstr* list = instr->attr.if_statem.list;
    printExpr(expr);
    lab_index+=1;
    printf("fjp L%d\n",lab_index);
    p_code(list);
    printf("ujp L%d\n",lab_index);
    printf("lab L%d\n",lab_index);
  }
  else if(instr->kind == I_IF_ELSE){
    ListInstr* expr = instr->attr.if_else_statem.expr;
    ListInstr* if_list = instr->attr.if_else_statem.list_if;
    ListInstr* else_list = instr->attr.if_else_statem.list_else;
    printExpr(expr);
    lab_index+=1;
    printf("fjp L%d\n",lab_index);
    p_code(if_list);
    lab_index+=1;
    printf("ujp L%d\n",lab_index);
    printf("lab L%d\n",lab_index-1);
    p_code(else_list);
    printf("lab L%d\n",lab_index);
  }
  else if(instr->kind == I_WHILE){
    ListInstr* expr = instr->attr.while_statem.expr;
    ListInstr* list = instr->attr.while_statem.list;
    //GERAL LABELS
    lab_index+=1;
    printf("lab L%d\n",lab_index);
    printExpr(expr);
    //GERAL LABELS
    lab_index+=1;
    printf("fjp L%d\n",lab_index);
    p_code(list);
    printf("ujp L%d\n",lab_index-1);
    printf("lab L%d\n",lab_index);
  }
}
//main caller to print PCODE
void PCODE(ListInstr* root){
  printf("P_CODE\n\n");
  printf("ent main\n");
  while (root!=NULL){
    p_code(root);
    root=root->next;
  }
  printf("ret\n");
  printf("stp\n");
}
//////////////                 END                //////////////




//////////////                BEGIN              //////////////
//////////////                MIPS               //////////////
//generating labels for MIPS
int lab_index_mips=0;
//print Instr with "expr type"
void MIPS_Expr(ListInstr* expr, int count, FILE *f) {

  if (expr == 0) {
    yyerror("Null expression!!");
  }
  else {
    Instr* instr = expr->instr;
    if (instr->kind == I_NUM) {
      fprintf(f,"%*c",count,' ');
      fprintf(f,"li $t1, %d\n",instr->attr.value);
    }
    else if (instr->kind == I_OP) {
      ListInstr* expr_left = instr->attr.op.expr_left;
      ListInstr* expr_right = instr->attr.op.expr_right;
      MIPS_Expr(expr_left,count,f);
      fprintf(f,"%*c",count,' ');
      fprintf(f,"move $t2, $t1\n");
      MIPS_Expr(expr_right,count,f);
      fprintf(f,"%*c",count,' ');
      fprintf(f,"move $t3, $t1\n");
      switch (instr->attr.op.operator) {
        case PLUS:
          fprintf(f,"%*c",count,' ');
          fprintf(f,"add $t1, $t2, $t3\n");
          break;
        case MULT:
          fprintf(f,"%*c",count,' ');
          fprintf(f,"mult $t1, $t2, $t3\n");
          break;
        case MINUS:
          fprintf(f,"%*c",count,' ');
          fprintf(f,"sub $t1, $t2, $t3\n");
          break;
        case DIV:
          fprintf(f,"%*c",count,' ');
          fprintf(f,"div $t1, $t2, $t3\n");
          break;
      }
    }
    else if (instr->kind == I_BOOLEAN){
      ListInstr* expr_left = instr->attr.boolean.expr_left;
      ListInstr* expr_right = instr->attr.boolean.expr_right;
      MIPS_Expr(expr_left,count,f);
      fprintf(f,"%*c",count,' ');
      fprintf(f,"move $t2, $t1\n");
      MIPS_Expr(expr_right,count,f);
      fprintf(f,"%*c",count,' ');
      fprintf(f,"move $t3, $t1\n");
      switch (instr->attr.boolean.operator) {
        case EQUAL:
          fprintf(f,"%*c",count,' ');
          fprintf(f,"bne $t2, $t3, L%d\n",lab_index_mips);
          break;
        case DIFF:
          fprintf(f,"%*c",count,' ');
          fprintf(f,"beq $t2, $t3, L%d\n",lab_index_mips);
          break;
        case LT:
          fprintf(f,"%*c",count,' ');
          fprintf(f,"bgt $t2, $t3, L%d\n",lab_index_mips);
          break;
        case GT:
          fprintf(f,"%*c",count,' ');
          fprintf(f,"blt $t2, $t3, L%d\n",lab_index_mips);
          break;
        case LTEQ:
          fprintf(f,"%*c",count,' ');
          fprintf(f,"bge $t2, $t3, L%d\n",lab_index_mips);
          break;
        case GTEQ:
          fprintf(f,"%*c",count,' ');
          fprintf(f,"ble $t2, $t3, L%d\n",lab_index_mips);
          break;
        case AND:
          fprintf(f,"%*c",count,' ');
          fprintf(f,"and $t1, $t2, $t3\n");
          fprintf(f,"%*c",count,' ');
          fprintf(f,"beq $t1, $zero, L%d\n",lab_index_mips);
          break;
        case OR:
          fprintf(f,"%*c",count,' ');
          fprintf(f,"or $t1, $t2, $t3\n");
          fprintf(f,"%*c",count,' ');
          fprintf(f,"beq $t1, $zero, L%d\n",lab_index_mips);
          break;
      }
    }
  }
}
//print other Instr
void MIPS_code(ListInstr* root, int count, FILE *f){
  Instr* instr = root->instr;

  if (instr->kind == I_DECL){
    char *var = instr->attr.decl.varName;
    if (checkVAR(var)==0){
      var_list = putVAR(var,var_list);
    }
  }
  else if (instr->kind == I_ATTR){
    char *var = instr->attr.attrvar.varName;
    if (checkVAR(var)==0){
      printf("Variable '%c' not declared!\n\n\n",*var);
      exit(-1);
    }
    ListInstr* expr = instr->attr.init.list_expr;
    fprintf(f,"%*c",count,' ');
    fprintf(f,"addi $sp, $sp, -4\n");
    MIPS_Expr(expr,count,f);
    fprintf(f,"%*c",count,' ');
    fprintf(f,"move $a1, $t1\n");
    fprintf(f,"%*c",count,' ');
    fprintf(f,"sw $a1, %s\n",var);
  }
  else if(instr->kind == I_ATTR_2){
    char *var = instr->attr.attrvar_2.varName1;
    if (checkVAR(var)==0){
      printf("Variable '%c' not declared!\n\n\n",*var);
      exit(-1);
    }
    fprintf(f,"%*c",count,' ');
    fprintf(f,"addi $sp, $sp, -4\n");
    fprintf(f,"%*c",count,' ');
    fprintf(f,"lw $a1, %s\n",var);
    fprintf(f,"%*c",count,' ');
    fprintf(f,"sw $a1, %s\n",instr->attr.attrvar_2.varName0);
  }
  else if(instr->kind == I_INIT){
    char *var = instr->attr.init.varName;
    if (checkVAR(var)==0){
      var_list = putVAR(var,var_list);
    }
    ListInstr* expr = instr->attr.init.list_expr;
    fprintf(f,"%*c",count,' ');
    fprintf(f,"addi $sp, $sp, -4\n");
    MIPS_Expr(expr,count,f);
    fprintf(f,"%*c",count,' ');
    fprintf(f,"move $a1, $t1\n");
    fprintf(f,"%*c",count,' ');
    fprintf(f,"sw $a1, %s\n",var);
  }
  else if(instr->kind == I_INIT_2){
    char *var = instr->attr.init_2.varName0;
    if (checkVAR(var)==0){
      var_list = putVAR(var,var_list);
    }
    fprintf(f,"%*c",count,' ');
    fprintf(f,"lw $a1, %s\n",instr->attr.init_2.varName1);
    fprintf(f,"%*c",count,' ');
    fprintf(f,"addi $sp, $sp, -4\n");
    fprintf(f,"%*c",count,' ');
    fprintf(f,"sw $a1, %s\n",var);
  }
  else if(instr->kind == I_SCAN){
    char *var = instr->attr.scan.varName;
    if (checkVAR(var)==0){
      printf("Variable '%c' not declared!\n\n\n",*var);
      exit(-1);
    }
    fprintf(f,"%*c",count,' ');
    fprintf(f,"li $v0, 5\n");
    fprintf(f,"%*c",count,' ');
    fprintf(f,"syscall\n");
    fprintf(f,"%*c",count,' ');
    fprintf(f,"move $a1, $v0\n");
    fprintf(f,"%*c",count,' ');
    fprintf(f,"sw $a1 %s\n",var);
  }
  else if(instr->kind == I_PRINT){
    ListInstr* expr = instr->attr.print.list_expr;
    MIPS_Expr(expr,count,f);
    fprintf(f,"%*c",count,' ');
    fprintf(f,"move $a0, $t1\n");
    fprintf(f,"%*c",count,' ');
    fprintf(f,"syscall\n");
  }
  else if(instr->kind == I_PRINT_VAR){
    char *var = instr->attr.print_var.varName;
    if (checkVAR(var)==0){
      printf("Variable '%c' not declared!\n\n\n",*var);
      exit(-1);
    }
    fprintf(f,"%*c",count,' ');
    fprintf(f,"lw $a1, %s\n", var);
    fprintf(f,"%*c",count,' ');
    fprintf(f,"li $v0, 1\n");
    fprintf(f,"%*c",count,' ');
    fprintf(f,"move $a0, $a1\n");
    fprintf(f,"%*c",count,' ');
    fprintf(f,"syscall\n");
  }
  else if(instr->kind == I_IF){
    ListInstr* expr = instr->attr.if_statem.expr;
    ListInstr* list = instr->attr.if_statem.list;
    lab_index_mips+=1;
    MIPS_Expr(expr,count,f);
    MIPS_code(list,count,f);
    fprintf(f,"%*c",count,' ');
    fprintf(f,"j L%d\n",lab_index_mips);
    fprintf(f,"L%d:\n",lab_index_mips);
  }
  else if(instr->kind == I_IF_ELSE){
    ListInstr* expr = instr->attr.if_else_statem.expr;
    ListInstr* if_list = instr->attr.if_else_statem.list_if;
    ListInstr* else_list = instr->attr.if_else_statem.list_else;
    lab_index_mips+=1;
    MIPS_Expr(expr,count,f);
    MIPS_code(if_list,count,f);
    lab_index_mips+=1;
    fprintf(f,"%*c",count,' ');
    fprintf(f,"j L%d\n",lab_index_mips);
    fprintf(f,"L%d:\n",lab_index_mips-1);
    MIPS_code(else_list,count,f);
    fprintf(f,"L%d:\n",lab_index_mips);
  }
  else if(instr->kind == I_WHILE){
    ListInstr* expr = instr->attr.while_statem.expr;
    ListInstr* list = instr->attr.while_statem.list;
    lab_index_mips+=1;
    fprintf(f,"L%d:\n",lab_index_mips);
    lab_index_mips+=1;
    MIPS_Expr(expr,count,f);
    MIPS_code(list,count,f);
    fprintf(f,"%*c",count,' ');
    fprintf(f,"j L%d\n",lab_index_mips-1);
    fprintf(f,"L%d:\n",lab_index_mips);
  }
}
//main caller to print PCODE
void MIPS(ListInstr* root,int count){

  FILE *f = fopen("mips.asm", "w");
  if (f == NULL) {
    printf("Error opening file!\n");
    exit(1);
  }
  fprintf(f,"%*c",count,' ');
  fprintf(f,".data\n");
  fprintf(f,"x:");
  fprintf(f,"%*c",count-2,' ');
  fprintf(f,".space 4\n");
  fprintf(f,"y:");
  fprintf(f,"%*c",count-2,' ');
  fprintf(f,".space 4\n");
  fprintf(f,"z:");
  fprintf(f,"%*c",count-2,' ');
  fprintf(f,".space 4\n");
  fprintf(f,"%*c",count,' ');
  fprintf(f,".text\n");
  fprintf(f,"main:\n");
  while (root!=NULL){
    MIPS_code(root,count,f);
    root=root->next;
  }
  fprintf(f,"%*c",count,' ');
  fprintf(f,"li $v0, 10\n");
  fprintf(f,"%*c",count,' ');
  fprintf(f,"syscall\n");
}
//////////////                 END                //////////////



//////////////                BEGIN              //////////////
//////////////                MAIN               //////////////
int main(int argc, char** argv) {
  --argc; ++argv;
  if (argc != 0) {
    yyin = fopen(*argv, "r");
    if (!yyin) {
      printf("'%s': could not open file\n", *argv);
      return 1;
    }
  }
  if (yyparse() == 0) {
    MIPS(root,6);
    PCODE(root);
  }
  return 0;
}
