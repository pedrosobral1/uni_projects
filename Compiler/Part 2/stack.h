
// Stack definitions
#ifndef __stack_h__
#define __stack_h__

// Instructions type
struct _Instr {
  enum {
    I_NUM,
    I_OP,
    I_BOOLEAN,
    I_DECL,
    I_ATTR,
    I_ATTR_2,
    I_INIT,
    I_INIT_2,
    I_SCAN,
    I_PRINT,
    I_PRINT_VAR,
    I_IF,
    I_IF_ELSE,
    I_WHILE
  } kind;
  union {
    int value;
    struct {
      int operator;
      struct _ListInstr* expr_left;
      struct _ListInstr* expr_right;
    } op;
    struct {
      int operator;
      struct _ListInstr* expr_left;
      struct _ListInstr* expr_right;
    } boolean;
    struct {
      char *varName;
    } decl;
    struct {
      char *varName;
      struct _ListInstr* expr;
    } attrvar;
    struct {
      char *varName0;
      char *varName1;
    } attrvar_2;
    struct {
      char *varName;
      struct _ListInstr* list_expr;
    } init;
    struct {
      char *varName0;
      char *varName1;
    } init_2;
    struct {
      char *varName;
    } scan;
    struct {
      struct _ListInstr* list_expr;
    } print;
    struct {
      char *varName;
    } print_var;
    struct {
      struct _ListInstr* expr;
      struct _ListInstr* list;
    } if_statem;
    struct {
      struct _ListInstr* list_if;
      struct _ListInstr* expr;
      struct _ListInstr* list_else;
    } if_else_statem;
    struct {
      struct _ListInstr* expr;
      struct _ListInstr* list;
    } while_statem;
  } attr;
};


//List of instructions
struct _ListInstr {
  struct _Instr* instr;
  struct _ListInstr* next;
};

typedef struct _Instr Instr; // Convenience typedef
typedef struct _ListInstr ListInstr;


// Constructor functions (see implementation in stack.c)
Instr* stack_ldc(int v);
Instr* stack_operation(int operator, ListInstr* left, ListInstr* right);
Instr* stack_operation_boolean(int operator, ListInstr* left, ListInstr* right);
Instr* stack_var_decl(char *varName);
Instr* stack_var_attr(char *varName, ListInstr* list);
Instr* stack_var_attr_2(char *varName0, char *varName1);
Instr* stack_var_init(char *varName, ListInstr* list);
Instr* stack_var_init_2(char *varName0, char *varName1);
Instr* stack_scan(char *varName);
Instr* stack_print_var(char *varName);
Instr* stack_print(ListInstr* list);
Instr* stack_if_statem(ListInstr* expr, ListInstr* list);
Instr* stack_if_else(ListInstr* expr,ListInstr* list0,ListInstr* list1);
Instr* stack_while(ListInstr* expr,ListInstr* list);

ListInstr* addnode(Instr* instr, ListInstr* list);
Instr* head(ListInstr* list);
Instr* tail(ListInstr* list);
ListInstr* append(ListInstr* list1, ListInstr* list2);



#endif
