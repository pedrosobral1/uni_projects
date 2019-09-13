
// AST definitions
#ifndef __ast_h__
#define __ast_h__

// AST for expressions
struct _Expr {
  enum {
    E_INTEGER,
    E_OPERATION,
    E_BOOLEAN
  } kind;
  union {
    int value; // for integer values
    struct {
      int operator; // PLUS, MINUS, ==, <, >= ...
      struct _Expr* left;
      struct _Expr* right;
    } op; // for binary expressions
  } attr;
};

// AST for commands
struct _Cmd {
  enum {
    C_LIST,
    C_DECL,
    C_ATTR,
    C_ATTR_2,
    C_INIT,
    C_INIT_2,
    C_SCAN,
    C_PRINT,
    C_PRINT_VAR,
    C_IF,
    C_IF_ELSE,
    C_WHILE
  } kind;
  union {
    struct{
      struct _Cmd* next;
    } cmd_list;
    struct { 
      char *varName;
    } declvr;
    struct {
      char *varName;
      struct _Expr* expr;
    } attrvar;
    struct {
      char *varName0;
      char *varName1;
    } attrvar_2;
    struct {
      char *varName;
      struct _Expr* expr;
    } init;
    struct {
      char *varName0;
      char *varName1;
    } init_2;
    struct { // scan var
      char *varName;
    } scan;
    struct { //print
      struct _Expr* expr;
      char *str;
    } print;
    struct { //print_Var
      char *varName;
    } print_var;
    struct {
      struct _Expr* expr;
      struct _Cmdlist* cmdlist;
    } if_statem;
    struct {
      struct _Expr* expr;
      struct _Cmdlist* cmdlist_if;
      struct _Cmdlist* cmdlist_else;
    } if_else;
    struct {
      struct _Expr* expr;
      struct _Cmdlist* cmdlist;
    } while_statem;
  } attr;
};

struct _Cmdlist { //list of commands
  struct _Cmd* command;
  struct _Cmdlist* next;
};


typedef struct _Expr Expr; // Convenience typedef
typedef struct _Cmd Cmd;
typedef struct _Cmdlist Cmdlist;



// Constructor functions (see implementation in ast.c)
Expr* ast_integer(int v);
Expr* ast_operation(int operator, Expr* left, Expr* right);
Expr* ast_operation_boolean(int operator, Expr* left, Expr* right);
Cmdlist* ast_cmd_list(Cmd* command, Cmdlist* next);
Cmd* ast_var_decl(char *var);
Cmd* ast_var_attr(char *var, Expr* expr);
Cmd* ast_var_attr_2(char *var0, char *var1);
Cmd* ast_var_init(char *var, Expr* expr);
Cmd* ast_var_init_2(char *var0, char *var1);
Cmd* ast_scan(char *var);
Cmd* ast_print(char *str,Expr* expr);
Cmd* ast_print_var(char *var);
Cmd* ast_if_statem(Expr* expr, Cmdlist* cmdlist);
Cmd* ast_if_else(Expr* expr, Cmdlist* cmd_if, Cmdlist* cmd_else);
Cmd* ast_while_statem(Expr* expr, Cmdlist* cmdlist);
#endif
