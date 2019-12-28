#include <stdio.h>
#include <dirent.h>
#include <stdlib.h>
#include <signal.h>
#include <ctype.h>
#include <unistd.h>
#include <fcntl.h>
#include <string.h>
#include <sys/types.h>
#include <sys/stat.h> 
int n;
int arg;
struct token{
	char **com;
};

struct token* toklist;
void parseline(int argc, char* argv[], FILE* file);
char ** cutter(FILE* file);
int parse_part(char ** tokens, int i);
void printer(char **);


void parseline(int argc, char* argv[], FILE* file){
	char ** tokens;
    int i = 0;
	if(file == stdin){
	printf("Line: ");}
	toklist = calloc(10, sizeof(struct token));
	tokens = cutter(file);
	while(i != -1){
	i = parse_part(tokens, i);}
}

char ** cutter(FILE* file){	
	char * input = malloc(512);
	char * string = malloc(512);
	char ** tokens = calloc(100, 512);
	int i = 0;
	n = 0;
	fgets(input, 512, file);
	input[strlen(input) - 1] = 0;
    if(input[0] == 0){kill(0, SIGALRM);}
	if(strlen(input) > 512){
		fprintf(stderr, "command too long\n");
		exit(-1);
	}	
	string = strtok(input, " ");
        if(string == NULL){
           fprintf(stderr, "empty line\n");
           exit(-1);
        }
	while (string != NULL) {
		tokens[i] = string;
		string = strtok(NULL, " ");
		i++;
	}
	return tokens;
	
}

int parse_part(char ** tokens, int i){
	int argc = 1;
	int out_set = 0;
	int index = 5;
    char * string = malloc(512);
	char** com = calloc(14, 128);
	char * buf = malloc(1);
	char * redirect_to = malloc(17);
    char * redirect_from = malloc(19);
	arg = 1;
	if(tokens[i] == 0){
		return -1;
	}
	sprintf(string, "%s", tokens[i]);
	com[0] = string;
    if(i == 0){
	com[1] = "original stdin";}
	else{
	sprintf(redirect_from, "pipe from stage %d", n-1);
	com[1] = redirect_from;	
	}
	com[2] = "original stdout";
	sprintf(buf, "%d", argc);
	com[3] = buf;
    com[4] = tokens[i];
	i++;
	for(i = i; tokens[i] != 0; i++){
	    if(strncmp(tokens[i], "|", 1) == 0){
			n++;
			arg = 1;
		    if(n == 10){
			    fprintf(stderr, "pipeline too deep\n");
			    exit(-1);
			   
		    }
			
		    if(tokens[i+1] == 0 || tokens[i+1][0] == '|'|| tokens[i+1][0] == '>' || tokens[i+1][0] == '<'){
                fprintf(stderr, "invalid null command\n");
		        exit(-1);
		    }
			if(out_set == 1){
				fprintf(stderr, "%s: ambiguous output\n", com[0]);
		        exit(-1);
			}
			i++;
			sprintf(redirect_to, "pipe to stage %d", n);
			com[2] = redirect_to;
			n--;
		    toklist[n].com = com;
			n++;
		    return i;
		   

	    }
        sprintf(string, "%s %s", string, tokens[i]);	
	if(arg == 10){
		fprintf(stderr, "%s: too many arguments\n", com[0]);
		exit(-1);
		
	}
	
	if(tokens[i][0] == '<'){
	   if(tokens[i+1] == 0 || tokens[i+1][0] == '|'|| tokens[i+1][0] == '>' || tokens[i+1][0] == '<'){
            fprintf(stderr, "%s: bad input redirection\n", com[0]);
	        exit(-1);
	   }
	   if(n > 0){
		    fprintf(stderr, "%s: ambiguous input\n", com[0]);
		    exit(-1);
	   }
           
	   i++;
           sprintf(string, "%s %s", string, tokens[i]);
           com[0] = string;
	   com[1] = tokens[i];
	}
	else if(tokens[i][0] == '>'){	
	   out_set = 1;
	   if(tokens[i+1] == 0 || tokens[i+1][0] == '|'|| tokens[i+1][0] == '>' || tokens[i+1][0] == '<'){
            fprintf(stderr, "%s: bad output redirection\n", com[0]);
	        exit(-1);
	    }
	   i++;
           sprintf(string, "%s %s", string, tokens[i]);
           com[0] = string;
	   com[2] = tokens[i];		
	}
	else{
	   argc++;
           com[0] = string;
	   sprintf(buf, "%d", argc);
	   com[3] = buf;	
	   com[index] = tokens[i];
	   index++;
	   arg++;
	   }

	}
	toklist[n].com = com;
	return -1;
}






