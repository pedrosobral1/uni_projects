// AST constructor functions

#include <stdlib.h> // for malloc
#include "ast.h" // AST header

Expr* ast_integer(int v) {
  Expr* node = (Expr*) malloc(sizeof(Expr));
  node->kind = E_INTEGER;
  node->attr.value = v;
  return node;
}

Expr* ast_operation (int operator, Expr* left, Expr* right) {
  Expr* node = (Expr*) malloc(sizeof(Expr));
  node->kind = E_OPERATION;
  node->attr.op.operator = operator;
  node->attr.op.left = left;
  node->attr.op.right = right;
  return node;
}

Expr* ast_operation_boolean (int operator, Expr* left, Expr* right) {
  Expr* node = (Expr*) malloc(sizeof(Expr));
  node->kind = E_BOOLEAN;
  node->attr.op.operator = operator;
  node->attr.op.left = left;
  node->attr.op.right = right;
  return node;
}


//struct to represent command nodes
Cmdlist* ast_cmd_list(Cmd* command0, Cmdlist* next0) {
  Cmdlist* node = (Cmdlist*) malloc(sizeof(Cmdlist));
  node->command = command0;
  node->next = next0;
  return node;
}


Cmd* ast_var_decl(char *var0) {
  Cmd* node = (Cmd*) malloc(sizeof(char));
  node->kind = C_DECL;
  node->attr.declvr.varName = var0;
  return node;
}

Cmd* ast_var_attr(char *var0, Expr* expr0) {
  Cmd* node = (Cmd*) malloc(sizeof(Expr));
  node->kind = C_ATTR;
  node->attr.attrvar.varName = var0;
  node->attr.attrvar.expr = expr0;
  return node;
}

Cmd* ast_var_attr_2(char *var0, char *var1) {
  Cmd* node = (Cmd*) malloc(sizeof(Expr));
  node->kind = C_ATTR_2;
  node->attr.attrvar_2.varName0 = var0;
  node->attr.attrvar_2.varName1 = var1;
  return node;
}

Cmd* ast_var_init(char *var0, Expr* expr0) {
  Cmd* node = (Cmd*) malloc(sizeof(Expr));
  node->kind = C_INIT;
  node->attr.init.varName = var0;
  node->attr.init.expr = expr0;
  return node;
}

Cmd* ast_var_init_2(char *var0, char *var1) {
  Cmd* node = (Cmd*) malloc(sizeof(Expr));
  node->kind = C_INIT_2;
  node->attr.init_2.varName0 = var0;
  node->attr.init_2.varName1 = var1;
  return node;
}

Cmd* ast_scan(char *var0) {
  Cmd* node = (Cmd*) malloc(sizeof(char));
  node->kind = C_SCAN;
  node->attr.scan.varName = var0;
  return node;
}

Cmd* ast_print(char* str0,Expr* expr0) {
  Cmd* node = (Cmd*) malloc(sizeof(char));
  node->kind = C_PRINT;
  node->attr.print.str = str0;
  if (expr0 != NULL) {
    node->attr.print.expr = expr0;
  }
  return node;
}
Cmd* ast_print_var(char* var0) {
  Cmd* node = (Cmd*) malloc(sizeof(char));
  node->kind = C_PRINT_VAR;
  node->attr.print_var.varName = var0;
  return node;
}

Cmd* ast_if_statem(Expr* expr0, Cmdlist* cmdlist0) {
  Cmd* node = (Cmd*) malloc(sizeof(Expr));
  node->kind = C_IF;
  node->attr.if_statem.expr = expr0;
  node->attr.if_statem.cmdlist = cmdlist0;
  return node;
}

Cmd* ast_if_else(Expr* expr0, Cmdlist* cmd_if, Cmdlist* cmd_else) {
  Cmd* node = (Cmd*) malloc(sizeof(Expr));
  node->kind = C_IF_ELSE;
  node->attr.if_else.expr = expr0;
  node->attr.if_else.cmdlist_if=cmd_if;
  node->attr.if_else.cmdlist_else=cmd_else;
  return node;
}

Cmd* ast_while_statem(Expr* expr0, Cmdlist* cmdlist0) {
  Cmd* node = (Cmd*) malloc(sizeof(Expr));
  node->kind = C_WHILE;
  node->attr.while_statem.expr = expr0;
  node->attr.while_statem.cmdlist = cmdlist0;
  return node;
}
