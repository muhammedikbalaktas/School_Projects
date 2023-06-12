

#include <unistd.h>     /* Symbolic Constants */
#include <sys/types.h>  /* Primitive System Data Types */ 
#include <errno.h>      /* Errors */
#include <stdio.h>      /* Input/Output */
#include <stdlib.h>     /* General Utilities */
#include <pthread.h>    /* POSIX Threads */
#include <string.h>     /* String handling */
#include <semaphore.h>  /* Semaphore */
#include <ctype.h>

typedef enum Condition{
    null,
    not_null
}condition;
typedef struct Arr_Data
{
    
    struct Arr_Data* next;
    condition con ;//is that index is readen by read thread
    int is_uppered;//check is data uppered by thread
    int is_replaced;//check is data replaced by thread
    int is_readed;
    int is_written;
    char * line;

}data;
typedef struct Threads{
    pthread_t thread;
    int id;
}threads;
typedef struct Flag{
    int upper;
    int replace;
    int index;
}flag;
data *root=NULL;

//**********************GLOBAL VARIABLES******************************
int number_of_reads;
int number_of_uppers;
int number_of_replacers;
int number_of_writer;

//number of lines inside file. Threads will get id accordingly (using mod)
int number_of_lines;


sem_t init_sem,
upper_sem,
replace_sem,
write_sem;
sem_t readers_sem;
sem_t upp_and_rep_sem;
sem_t upp_rep_sem;
//threads
pthread_t init_thread;

// pthread_t reader[];
// pthread_t upper[];
// pthread_t replacer[];
// pthread_t writer[];
//number of threads which will be used inside their function


//**********************GLOBAL VARIABLES******************************


//***************FUNCTION PROTOTYPE*******************************
void add_node(data**root);
void print_list(data ** root);
void parse_from_file(int index,int id);
int is_null(data **root,int index);
void replace_letter(data ** root,int index);
void convert_to_upper(data ** root,int index);
void add_node(data**root);
void add_node_with_data(data**root,char *line,int index);
void get_number_of_line();
int calculate_number_of_operations(int size,int id);
//void replace_or_upper(int replace,int upper,int index);
//***************FUNCTION PROTOTYPE*******************************

int is_null(data ** root,int index){
    data* iter=*root;
    
    
    
    for (int i = 0; i < index; i++)
    {
        iter=iter->next;
    }
    return iter->con;
}
void convert_to_upper(data ** root,int index){
    //convert the data inside linked list to uppercase
    data* iter=*root;
    
    
    
    for (int i = 0; i < index; i++)
    {
        iter=iter->next;
    }
    iter->is_uppered=1;
    for (int i = 0; iter->line[i]; i++) {
        iter->line[i] = toupper(iter->line[i]);
    }
    

}
void replace_letter(data ** root,int index){
    data* iter=*root;
    
    
    
    for (int i = 0; i < index; i++)
    {
        iter=iter->next;
    }
    int i = 0;
    while (iter->line[i] != '\0') {
        if (iter->line[i] == ' ') {
            iter->line[i] = '_';
        }
        i++;
    }
    iter->is_replaced=1;
    
}
pthread_mutex_t mutex;
void *replace_or_upper(void * flags){
    //this function is synchronization for both upper and replacer thread
    flag *f =(flag*)flags;
    pthread_mutex_lock(&mutex);
    if(f->replace==1){
        replace_letter(&root,f->index);
    }
    else{
        convert_to_upper(&root,f->index);
    }
    pthread_mutex_unlock(&mutex);
    

}
void *init_linked_list(void * number){//initilize the linked list according to size of file
    
    int size=*(int*)number;
    for (int i = 0; i < size; i++)
    {
        add_node(&root);
        
    }
    sem_post(&init_sem);
    

}


void add_node(data**root){//add node for linked list
    //this part is just for initialization
    data* new_node=(data*) malloc(sizeof(data));
    new_node->con=null;
    new_node->is_replaced=0;
    new_node->is_uppered=0;
    new_node->next=null;
    if (*root==NULL)        
    {
        *root=new_node;
    }
    else{
        data *iter=*root;
        while (iter->next!=NULL)
        {
            iter=iter->next;
        }
        iter->next=new_node;
        
    }
    
}
void add_node_with_data(data**root,char *line,int index){
    //this part is for addition
    data* iter=*root;
    
    
    
    for (int i = 0; i < index; i++)
    {
        iter=iter->next;
    }
    iter->line=malloc(sizeof(strlen(line)));
    strcpy(iter->line,line);
    iter->con=not_null;
    iter->is_replaced=0;
    iter->is_uppered=0;
    
}
void print_list(data ** root){
    if (*root==NULL)
    {
        printf("linked list is empty\n");
        return;
    }
    
    data* iter=*root;
    
    int i =0;
    while (iter!=NULL)
    {
        printf("i is %d data is %s \n",i,iter->line);
        i++;
        iter=iter->next;
    }
    

}
void get_number_of_line(){
    FILE *file = fopen("deneme.txt", "r");
    if (file == NULL) {
        printf("Cannot open file\n");
        return;
    }

    char line[1000];
    int line_count = 0;
    while (fgets(line, sizeof(line), file)) {
        line_count++;
    }

    
    fclose(file);
    number_of_lines=line_count;
}
int calculate_number_of_operations(int size,int id){
    //this function is for calculating for each thread
    //that how many times each thread will work.
    int n=number_of_lines;
    int result=0;
    for (int i = 0; i < number_of_lines; i++)
    {
        if(i%size==id)result++;
        
    }
    
    return result;
    
}
void parse_from_file(int index,int id){
    //this function reads from file and parses its data inside linked list
    //using threads
    FILE *fp;
    fp = fopen("deneme.txt", "r");
    if (fp == NULL) {
        printf("Error opening file\n");
        return ;
    }

    
    char line[100];
    int line_number = 0;
    while (fgets(line, sizeof(line), fp)) {
        if (line_number == index) {
            
            add_node_with_data(&root,line, index);
            break;
        }
        line_number++;
    }
    if (line_number != index) {
        printf("Error: index out of bounds\n");
    }

    fclose(fp);
}

void *read_from_file(void* data){//for thread
    
    //data is thread id and the id will be given according their modulus
    sem_wait(&readers_sem);
    int id=*(int*)data;
    
    //printf("thread is is %d i should process %d times\n",id);
    for (int i = 0; i < number_of_lines; i++)
    {

        if(i%number_of_reads==id){
            
            parse_from_file(i,id);
            sem_post(&upp_rep_sem);
            
        }
    }
    sem_post(&readers_sem);
    



}

void *upper_letter(void *data){//for thread
    
    sem_wait(&upp_rep_sem);//if read is completed at somewhere
    int id=*(int*)data;
    
    
    int n=calculate_number_of_operations(number_of_uppers,id);
    

    while (n!=0)//since the number of line can be higher than thread amount
    //each thread should handle multiple lines n is the number of line they should handle
    {
                for (int i = 0; i < number_of_lines; i++)
                {
                    if(i%2==id){
                        if (is_null(&root,i)==1)//if linked list is not null
                        {
                            flag *f=(flag*) malloc (sizeof(flag));
                            f->index=i;
                            f->upper=1;
                            f->replace=0;
                            pthread_t t;
                            pthread_create(&t,NULL,replace_or_upper,(void*)f);
                            
                            
                            
                            n--;
                            if(n==0)break;

                            
                            
                        }
                        
                    }

                }
                
    }
    sem_post(&upp_rep_sem);
    
    

}

void *replace(void * data){
    sem_wait(&upp_rep_sem);//if read is completed at somewhere
    int id=*(int*)data;
    
    
    int n=calculate_number_of_operations(number_of_replacers,id);
    

    while (n!=0)//since the number of line can be higher than thread amount
    //each thread should handle multiple lines n is the number of line they should handle
    {
                for (int i = 0; i < number_of_lines; i++)
                {
                    if(i%3==id){
                        if (is_null(&root,i)==1)//if linked list is not null
                        {
                            flag *f=(flag*) malloc (sizeof(flag));
                            f->index=i;
                            f->upper=0;
                            f->replace=1;
                            pthread_t t;
                            pthread_create(&t,NULL,replace_or_upper,(void*)f);
                            
                            
                            n--;
                            if(n==0)break;

                            
                            
                        }
                        
                    }

                }
                
    }
    sem_post(&upp_rep_sem);
    
}


int main(int argc, char *argv[])
{
    for (int i = 0; i < argc; i++) {
        
        if(i==2){
            printf("you should have file name that name is deneme.txt\n");
        }
        if (i==4)
        {
            //read thread
            number_of_reads = atoi(argv[4]);
            
        }
        if(i==5){
            //upper thread
            number_of_uppers = atoi(argv[5]);
            
        }
        if(i==6){
            //replace thread
            number_of_replacers = atoi(argv[6]);
            
        }
        if(i==7){
            //write thread
            number_of_writer = atoi(argv[7]);
        }
        
    }
    get_number_of_line();
    pthread_mutex_init(&mutex, NULL);
    //semaphores
    sem_init(&init_sem, 0, 0);
    sem_init(&readers_sem, 0, 1);
    //sem_init(&upper_sem,0,0);
    //sem_init(&replace_sem,0,0);
    sem_init(&upp_and_rep_sem,0,1);
    sem_init(&upp_rep_sem,0,0);
    //semaphores

    pthread_create(&init_thread,NULL,init_linked_list,(void*)&number_of_lines);
    
    sem_wait(&init_sem);//azaltır
    
    
    pthread_join(init_thread,NULL);
    
    
    sem_destroy(&init_sem);
    threads readers[number_of_reads];
    threads uppers[number_of_uppers];
    threads replacers[number_of_replacers];
    for (int i = 0; i < number_of_replacers; i++)
    {
        replacers[i].id=i;//each thread will have a unique id inside loop
    }
    for (int i = 0; i < number_of_reads; i++)
    {
        readers[i].id=i;//each thread will have a unique id inside loop
    }
    for (int i = 0; i < number_of_uppers; i++)
    {
        uppers[i].id=i;
    }


    for (int i = 0; i < number_of_uppers; i++)
    {
        pthread_create(&uppers[i].thread,NULL,upper_letter,&(uppers[i].id));
    }
    for (int i = 0; i < number_of_replacers; i++)
    {
        pthread_create(&replacers[i].thread,NULL,replace,&(replacers[i].id));
        
    }
    
    for (int i = 0; i < number_of_reads; i++)
    {

            pthread_create(&readers[i].thread,NULL,read_from_file,&(readers[i].id));
            

    }
    sem_destroy(&readers_sem);
    sem_destroy(&upper_sem);
    sem_destroy(&replace_sem);
    sem_destroy(&upp_and_rep_sem);
    for (int i = 0; i < number_of_uppers; i++)//in order to wait all readers inside main
    {
        pthread_join(uppers[i].thread,NULL);
    }
    for (int i = 0; i < number_of_reads; i++)//in order to wait all readers inside main
    {
        pthread_join(readers[i].thread,NULL);
    }
    for (int i = 0; i < number_of_replacers; i++)//in order to wait all readers inside main
    {
        pthread_join(replacers[i].thread,NULL);
    }
    
    
    printf("continue by main thread\n");
    print_list(&root);
    
    
    
}
