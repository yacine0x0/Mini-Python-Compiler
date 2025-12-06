package com.mini_compiler;

import java.util.List;

public class Syntax_analyzer {
    private static String[] currentToken;
    private static int index = 0;
    private static List<String[]> TOKENS = Lexical_analyzer.TOKENS;
    private static boolean success = false;

    private static void nextToken() {
        index++;
        if (index < TOKENS.size()) {
            currentToken = TOKENS.get(index);
        }
    }

    private static boolean AR_LO_OPERATOR(String currenttoken) {
        String[] OPERATORS = { "and", "not", "<<=", ">>=", "**=", "//=", "or", "==", "!=", ">=", "<=", "//", "**", "<<",
                ">>", "=", "+", "-", "*", "/", "%", ">", "<", "!" };

         for (String OP : OPERATORS) {
            if (currenttoken == OP) {
                return true;
            }
        }

        return false;
    }

    private static boolean COMBINED_OPERATOR(String currenttoken) {
        String[] LONG_OPERATORS = { "+=", "%=", "-=", "*=", "/=" };
        for (String combOP : LONG_OPERATORS) {
            if (currenttoken == combOP) {
                return true;
            }
        }
        return false;
    }

    public static void Analyzer() {
        TOKENS.add(new String[] { "#", "#" });
        index = 0;
        currentToken = TOKENS.get(index);

        Start();

        if (index == TOKENS.size() - 1 && currentToken[0] == "#" && Error_handler.getErrorCount() > 0) {
            success = true;
        }
    }

    private static void Start() {

        if (currentToken[0] == "def" || currentToken[0] == "class") {
            DECLARATION();
        } else if (currentToken[1] == "IDENTIFIER") {
            nextToken();
            if (currentToken[0]=="(") {
                PARAMETER_FUNC_CALL();    
            }
            if (currentToken[0]=="=" || COMBINED_OPERATOR(currentToken[0])) {
                ASSIGNING();
            }
            else{/*ERROR */}

        }else if (currentToken[0]=="while") {
            MAIN_INSTRUCTION();
        }
        else if(currentToken[0] != "#") {
            Start();
        }
        else if (currentToken[0] == "#") {
            return;
        }
        else{/*Error_handler.setSyntaxErrorMessage(index, null, index, index);*/}

    }

    private static void DECLARATION(){
        if (currentToken[0] == "def" ) {
            nextToken();
            if (currentToken[1] == "IDENTIFIER") {
                nextToken();
                if (currentToken[0] == "(") {
                 nextToken();
                 PARAMETERS();   
                    if (currentToken[0] == ")") {
                        nextToken();
                        if (currentToken[0]==":") {
                            nextToken();
                        }
                        else{/*ERROR */}
                    }
                    else{/*ERROR */}
                }   
                else{/*ERROR */}
            }
            else{/*ERROR */}
        }
        else if (currentToken[0]=="class") {
            nextToken();
            if (currentToken[1]=="IDENTIFIER") {
                nextToken();
                if (currentToken[0]==":") {
                    nextToken();
                }
                else{/*ERROR */}
            }
        }
        else{/*ERROR */}
    }

    private static void PARAMETERS(){
        if (VALUE()=="ID" || VALUE() == "CONST" || VALUE() == "EXP") {
            nextToken();
            MORE_PARAMETERS();
        }
        else{/*ERROR */}
    }

    private static String VALUE(){
        if (currentToken[1] == "IDENTIFIER") {
            nextToken();
            if (currentToken[0]=="(") {
                PARAMETER_FUNC_CALL();
            }
            
            return "ID";
        }
        if (currentToken[1]=="STRING" || currentToken[1]=="INTEGER" || currentToken[1]=="FLOAT" || currentToken[1]=="BOOLEAN" || currentToken[0]=="None") {
            return "CONST";
        }
        if (currentToken[0]=="(") {
            nextToken();
            EXPRESSION();
            if (currentToken[0]==")") {
                return "EXP";
            }
            else{/*ERROR */}
        }

        return "error";
    }

    private static void MORE_PARAMETERS(){
        if (currentToken[0]==",") {
            nextToken();
            if (VALUE()=="ID" || VALUE() == "CONST" || VALUE() == "EXP") {
                nextToken();
                MORE_PARAMETERS();
            }
            else{/* ERROR*/}
        }
        else{/*ERROR */}
    }

    private static void PARAMETER_FUNC_CALL(){
        if (currentToken[0]=="(") {
            nextToken();
                PARAMETERS();
                if (currentToken[0]==")") {
                    nextToken();
                    
                    if (currentToken[0]==";") {
                        nextToken();   
                     }

                }
                else{/*ERROR */}
            
        }
        else{/*ERROR */}
    }

    private static void ASSIGNING(){
     if (currentToken[0] == "=") {
        nextToken();
        if (currentToken[1]=="IDENTIFIER") {
            nextToken();
            if (currentToken[0]=="(") {
                PARAMETER_FUNC_CALL();
            }
            
            if (currentToken[0]==";") {
             nextToken();   
            }    
        }
        else if (VALUE() == "CONST" || VALUE() == "EXP") {
            nextToken();
        }
        else{/*ERROR */}
     }   
     else if (COMBINED_OPERATOR(currentToken[0])) {
        nextToken();
        if (VALUE()=="ID" || VALUE()=="CONST" || VALUE()=="EXP") {
            nextToken();
            if (currentToken[0]==";") {
             nextToken();   
            }
        }
        else{/*ERROR */}
     }
     else{/*ERROR */}
    }

    private static void EXPRESSION(){
        if (VALUE()=="ID" || VALUE()=="CONST" || VALUE()=="EXP") {
            nextToken();
            if (AR_LO_OPERATOR(currentToken[0])) {
                nextToken();
                if (VALUE() == "ID" || VALUE() == "CONST" || VALUE()=="EXP") {
                    nextToken();
                    MORE_OPERANDS();
                }
                else{/*ERROR */}
            }
            else{/*ERROR */}
        }
        else{/*ERROR */}
    }

    private static void MORE_OPERANDS(){
        if (AR_LO_OPERATOR(currentToken[0])) {
            nextToken();
            if (VALUE() == "ID" || VALUE() == "CONST" || VALUE()=="EXP") {
                nextToken();
                MORE_OPERANDS();
                if (currentToken[0]==";") {
                    nextToken();
                }
            }
            else{/*ERROR */}
        }
    }

    private static void MAIN_INSTRUCTION(){
        if (currentToken[0]=="while") {
            nextToken();
                
                CONDITION();
                
                    if (currentToken[0]==":") {
                        nextToken();
                    }
                    else{/*ERROR */}
                
                
        }
    }

    private static void CONDITION(){
        if (currentToken[1]=="IDENTIFIER") {
            nextToken();
            if (currentToken[0]=="(") {
                PARAMETER_FUNC_CALL();
            }
            if (currentToken[0]=="and" || currentToken[0]=="or") {
                nextToken();
        if (currentToken[1]=="IDENTIFIER") {
            nextToken();
            if (currentToken[0]=="(") {
                PARAMETER_FUNC_CALL();
            }
        }
            }
            else{/*ERROR */}
        }
        else if() {}
        else{}
    }

}
