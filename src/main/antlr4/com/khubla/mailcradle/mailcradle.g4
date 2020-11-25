/*
BSD License

Copyright (c) 2020, Tom Everett
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions
are met:

1. Redistributions of source code must retain the above copyright
   notice, this list of conditions and the following disclaimer.
2. Redistributions in binary form must reproduce the above copyright
   notice, this list of conditions and the following disclaimer in the
   documentation and/or other materials provided with the distribution.
3. Neither the name of Tom Everett nor the names of its contributors
   may be used to endorse or promote products derived from this software
   without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
grammar mailcradle;

mailcradle
   : (list | filter | import_)+
   ;

import_
   : 'import' string ';'
   ;

list
   : 'list' identifier string (',' string)* ';'
   ;

filter
   : 'if' '(' condition+ ')' '{' action+ '}' ';'
   ;

condition
   : termcondition
   | listcondition
   ;

termcondition
   : term termrelation string
   ;

listcondition
   : identifier listrelation term
   ;

term
   : subjecterm
   | fromterm
   | bodyterm
   | headerterm
   ;

subjecterm
   : 'subject'
   ;

bodyterm
   : 'body'
   ;

fromterm
   : 'from'
   ;

headerterm
   : 'header' '[' string ']'
   ;

termrelation
   : 'contains'
   | 'is'
   ;

listrelation
   : 'contains'
   ;

action
   : moveaction
   | forwardaction
   | replyaction
   | flagaction
   | unflagaction
   | setheaderaction
   | removeheaderaction
   ;

setheaderaction
   : 'setheader' string string
   ;

removeheaderaction
   : 'removeheader' string
   ;

flagaction
   : 'flag' string ';'
   ;

unflagaction
   : 'unflag' string ';'
   ;

replyaction
   : 'replywith' string ';'
   ;

moveaction
   : 'moveto' string ';'
   ;

forwardaction
   : 'forwardto' string ';'
   ;

identifier
   : IDENTIFIER
   ;

string
   : STRING_LITERAL
   ;

STRING_LITERAL
   : '"' .*? '"'
   ;

IDENTIFIER
   : [A-Za-z] [A-Za-z0-9]*
   ;

LINECOMMENT
   : '//' ~ [\r\n]* -> skip
   ;

WS
   : [ \t\r\n]+ -> skip
   ;

