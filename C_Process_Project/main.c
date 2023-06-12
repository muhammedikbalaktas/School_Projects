

#include <stdio.h>
#include <unistd.h>
#include <errno.h>
#include <stdlib.h>
#include <string.h>
#include <sys/wait.h>
#define MAX_LINE 80 /* 80 chars per line, per command, should be enough. */
 
/* The setup function below will not return any value, but it will just: read
in the next command line; separate it into distinct arguments (using blanks as
delimiters), and set the args array entries to point to the beginning of what
will become null-terminated, C-style strings. */
typedef struct History{
	char * first_line;
	char * full_line;

	int flag;
	struct History * next;
	int size;
	char* final_args[];
	

}history;
history * root_of_history;
typedef struct Path{
	char * data;
	struct  Path* next;
}path;

path* root_of_path;


int get_size_of_history(history **root);
void execute(char * path, char * args[],int flag);
char * get_executable__path(path **root,char * input);
void add_to_history(history **root,char * full_line,char *args[],int flag,int size);

void print_history(history ** root){
	if (*root==NULL)
	{
		printf("history is empty\n");
	}
	else{
		history *iter=*root;
		int i=0;
		while (iter!=NULL)
		{	
			
			printf("\n%d-) %s",i,iter->full_line);
			
			if(iter->flag==1) printf(" &");
			printf("\n");
			i++;
			if (i==10)break;
			
			iter=iter->next;
		}
		
	}
	
	
}



void add_to_history(history **root,char * full_line,char *args[],int flag,int size){
	history *new_node=(history*) malloc(sizeof(history));

	 new_node->next=NULL;
	 new_node->full_line=malloc(30);
	new_node->first_line=malloc(strlen(args[0]));
	strcpy(new_node->first_line,args[0]);
	
	new_node->full_line=full_line;
	new_node->size=size;
	new_node->flag=flag;
	new_node->final_args[size];
	for (int i = 0; i < size; i++)
	{
		
		new_node->final_args[i]=malloc(strlen(args[i]));
		strcpy(new_node->final_args[i],args[i]);
	}
	new_node->final_args[size]=NULL;
	//char *final_args[size];
	if (*root==NULL)
	{
		
		*root=new_node;
	}
	else{
		new_node->next=*root;
		*root=new_node;
		
	}
	
	
}


void add_to_path(path **root,char * data){
	path *new_node=(path*) malloc(sizeof(path));
	new_node->next=NULL;
	new_node->data=malloc(30);
	new_node->data=data;
	if (*root==NULL)
	{
		
		*root=new_node;
	}
	else{
		path *iter=*root;
		while (iter->next!=NULL)
		{
			iter=iter->next;
		}
		iter->next=new_node;
		
	}
	
}
void print_list_of_path(path ** root){
	if (*root==NULL)
	{
		printf("linked list is empty\n");
	}
	else{
		path *iter=*root;
		while (iter!=NULL)
		{
			printf("%s\n",iter->data);
			
			iter=iter->next;
		}
		
	}
	
}
void get_all_directories(char input[]){//get all directories from path with : split

    
    char *identifier = ":";

    
    char *piece = strtok(input, identifier);

    
    while (piece != NULL) {
	
    add_to_path(&root_of_path,piece);
	
	
	
    piece = strtok(NULL, identifier);
	
	
  }
    

}

char * is_executable(char *path,char *input){//get the path of executable if it is executable
	//char *ls = "ls ";
	//check if given input is executable (like ls or pwd) it returns 1 if it is 
	char * slash="/";
	char *final_path=malloc(strlen(path)+strlen(slash)+1);
	strcpy(final_path,path);
	
	strcat(final_path,slash);
	char *combined_string = (malloc(strlen(final_path)+strlen(input)+1));
	strcpy(combined_string, final_path); 
  	strcat(combined_string, input);

	if (access(combined_string, X_OK) == 0) {
        
		return combined_string;
    } else {
        //printf("The directory %s is not executable\n",combined_string);
        
		return NULL;
    }

}

char * get_executable__path(path **root,char * input){
	if (*root==NULL)
	{
		printf("linked list is empty\n");
	}
	else{
		path *iter=*root;
		while (iter!=NULL)
		{
			//printf("%s\n",iter->data);
			if (is_executable(iter->data,input)!=NULL)
			{
				//printf("%s is executable and path is %s\n",input,iter->data);
				return is_executable(iter->data,input);
			}
			
			
			iter=iter->next;
		}
		printf("%s is not executable\n",input);
		return NULL;
		
	}
}
void init(){
	FILE* path_output_file = popen("echo $PATH", "r");

	
	char path_output[5000];
	fgets(path_output,sizeof(path_output),path_output_file);
	
	
	

	
	pclose(path_output_file);

  	get_all_directories(path_output);
}
void execute(char * path, char * args[],int flag){//executes according to given inputs
        
		
		
		pid_t process;
		if (!flag)
		{
			process=fork();
			if(process==0){
				execv(path, args);
  				perror("execv failed");
			}
			else{
				
				int status;
				pid_t w = wait(&status);
				if (w == -1) {
					perror("wait");
					exit(EXIT_FAILURE);
				}
				
			}
		}
		else{
			process=fork();
			if(process==0){
				execv(path, args);
  				perror("execv failed");
			}
		}
		
		
}
int get_size_of_history(history **root){
		if (*root==NULL)
	{
		printf("history is empty\n");
		return -1;
	}
	else{
		history *iter=*root;
		int i=0;
		while (iter!=NULL)
		{
			
			i++;
			if (i==10)return 9;
			
			iter=iter->next;
		}
		return i;
		
	}
}

void decide_exectuable_type(char * first_line,char * args[],int size){//this function get the full_line of setup function and for foreground or background process
	
    int a;
	char *result=malloc(50);
	int flag=0;
	
	
	
	for (a = 0; args[a] != NULL; a++) {//check if it is a background process
		

		if(strcmp(args[a],"&")==0) {
			flag=1;
			break;
		}
		strcat(result,args[a]);
		strcat(result," ");
		
		
		
	}
	char *final_args[size-flag];
	int s=0;
	for (int i = 0; i < (size-flag); i++,s++)
	{
		final_args[i]=malloc(strlen(args[i]));
		strcpy(final_args[i],args[i]);
	}
	final_args[s]=NULL;
	
	//printf("size is %d\n",s);
	
	
	//history **root,char * full_line,char *args[],int flag
	
	if(strcmp(first_line,"history")==0){
		print_history(&root_of_history);
		
		
	}
	else{
		add_to_history(&root_of_history,result,args,flag,size);
		execute(get_executable__path(&root_of_path,first_line),final_args,flag);
	}
	
	
	
	
    
    
    

}
void setup(char inputBuffer[], char *args[],int *background){
    int length, /* # of characters in the command line */
        i,      /* loop index for accessing inputBuffer array */
        start,  /* index where beginning of next command parameter is */
        ct;     /* index of where to place the next parameter into args[] */
    
    ct = 0;
        
    /* read what the user enters on the command line */
    length = read(STDIN_FILENO,inputBuffer,MAX_LINE);  

    /* 0 is the system predefined file descriptor for stdin (standard input),
       which is the user's screen in this case. inputBuffer by itself is the
       same as &inputBuffer[0], i.e. the starting address of where to store
       the command that is read, and length holds the number of characters
       read in. inputBuffer is not a null terminated C-string. */

    start = -1;
    if (length == 0)
        exit(0);            /* ^d was entered, end of user command stream */

/* the signal interrupted the read system call */
/* if the process is in the read() system call, read returns -1
  However, if this occurs, errno is set to EINTR. We can check this  value
  and disregard the -1 value */
    if ( (length < 0) && (errno != EINTR) ) {
        perror("error reading the command");
	exit(-1);           /* terminate with error code of -1 */
    }

	printf(">>%s<<",inputBuffer);
    for (i=0;i<length;i++){ /* examine every character in the inputBuffer */

        switch (inputBuffer[i]){
	    case ' ':
	    case '\t' :               /* argument separators */
		if(start != -1){
                    args[ct] = &inputBuffer[start];    /* set up pointer */
		    ct++;
		}
                inputBuffer[i] = '\0'; /* add a null char; make a C string */
		start = -1;
		break;

            case '\n':                 /* should be the final char examined */
		if (start != -1){
                    args[ct] = &inputBuffer[start];     
		    ct++;
		}
                inputBuffer[i] = '\0';
                args[ct] = NULL; /* no more arguments to this command */
		break;

	    default :             /* some other character */
		if (start == -1)
		    start = i;
                if (inputBuffer[i] == '&'){
		    *background  = 1;
                    inputBuffer[i-1] = '\0';
		}
		else{
			*background=0;
		}
	} /* end of switch */
     }    /* end of for */
     args[ct] = NULL; /* just in case the input line was > 80 */

	
	//printf("size is %d\n",ct);
	
	decide_exectuable_type(args[0],args,ct);

    
    //printf("args is %s \n",args);
} /* end of setup routine */


int main(void)
{
            init();
            char inputBuffer[MAX_LINE]; /*buffer to hold command entered */
            int background; /* equals 1 if a command is followed by '&' */
            char *args[MAX_LINE/2 + 1]; /*command line arguments */
            while (1){
                        printf("myshell: ");
                        background = 1;
                        
                        /*setup() calls exit() when Control-D is entered */
                        setup(inputBuffer, args, &background);
                        
                        
                        /** the steps are:
                        (1) fork a child process using fork()
                        (2) the child process will invoke execv()
						(3) if background == 0, the parent will wait,
                        otherwise it will invoke the setup() function again. */
						//printf("background is %d\n",background);




                       
            }
}
