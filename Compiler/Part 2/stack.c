// STACK constructor functions

#include <stdlib.h> // for malloc
#include "stack.h" // STACK header



//Instr type nodes
Instr* stack_ldc(int v0) {
  Instr* node = (Instr*) malloc(sizeof(Instr));
  node->kind = I_NUM;
  node->attr.value = v0;
  return node;
}

Instr* stack_operation(int operator, ListInstr* left, ListInstr* right) {
  Instr* node = (Instr*) malloc(sizeof(Instr));
  node->kind = I_OP;
  node->attr.op.operator = operator;
  node->attr.op.expr_left = left;
  node->attr.op.expr_right = right;
  return node;
}

Instr* stack_operation_boolean(int operator, ListInstr* left, ListInstr* right) {
  Instr* node = (Instr*) malloc(sizeof(Instr));
  node->kind = I_BOOLEAN;
  node->attr.boolean.operator = operator;
  node->attr.boolean.expr_left = left;
  node->attr.boolean.expr_right = right;
  return node;
}

Instr* stack_var_decl(char *var) {
  Instr* node = (Instr*) malloc(sizeof(Instr));
  node->kind = I_DECL;
  node->attr.decl.varName = var;
  return node;
}

Instr* stack_var_attr(char *var, ListInstr* expr) {
  Instr* node = (Instr*) malloc(sizeof(Instr));
  node->kind = I_ATTR;
  node->attr.attrvar.varName = var;
  node->attr.attrvar.expr = expr;
  return node;
}

Instr* stack_var_attr_2(char *var0, char *var1) {
  Instr* node = (Instr*) malloc(sizeof(Instr));
  node->kind = I_ATTR_2;
  node->attr.attrvar_2.varName0 = var0;
  node->attr.attrvar_2.varName1 = var1;
  return node;
}

Instr* stack_var_init(char *var, ListInstr* expr) {
  Instr* node = (Instr*) malloc(sizeof(Instr));
  node->kind = I_INIT;
  node->attr.init.varName = var;
  node->attr.init.list_expr = expr;
  return node;
}

Instr* stack_var_init_2(char *var0, char *var1) {
  Instr* node = (Instr*) malloc(sizeof(Instr));
  node->kind = I_INIT_2;
  node->attr.init_2.varName0 = var0;
  node->attr.init_2.varName1 = var1;
  return node;
}

Instr* stack_scan(char *var) {
  Instr* node = (Instr*) malloc(sizeof(Instr));
  node->kind = I_SCAN;
  node->attr.scan.varName = var;
  return node;
}

Instr* stack_print_var(char *var) {
  Instr* node = (Instr*) malloc(sizeof(Instr));
  node->kind = I_PRINT_VAR;
  node->attr.print_var.varName = var;
  return node;
}

Instr* stack_print(ListInstr* expr) {
  Instr* node = (Instr*) malloc(sizeof(Instr));
  node->kind = I_PRINT;
  node->attr.print.list_expr = expr;
  return node;
}

Instr* stack_if_statem(ListInstr* bool, ListInstr* list) {
  Instr* node = (Instr*) malloc(sizeof(Instr));
  node->kind = I_IF;
  node->attr.if_statem.expr = bool;
  node->attr.if_statem.list = list;
  return node;
}

Instr* stack_if_else(ListInstr* bool, ListInstr* list_if, ListInstr* list_else) {
  Instr* node = (Instr*) malloc(sizeof(Instr));
  node->kind = I_IF_ELSE;
  node->attr.if_else_statem.expr = bool;
  node->attr.if_else_statem.list_if = list_if;
  node->attr.if_else_statem.list_else = list_else;
  return node;
}

Instr* stack_while(ListInstr* bool, ListInstr* list) {
  Instr* node = (Instr*) malloc(sizeof(Instr));
  node->kind = I_WHILE;
  node->attr.while_statem.expr = bool;
  node->attr.while_statem.list = list;
  return node;
}

//Add Instr to ListInstr
ListInstr* addnode(Instr* instr0, ListInstr* list0){
  ListInstr* node = (ListInstr*) malloc(sizeof(ListInstr));
  node->instr = instr0;
  node->next = list0;
  return node;
}
