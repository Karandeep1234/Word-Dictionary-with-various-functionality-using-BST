//import java.io.*;
import java.util.*;


public class dictionary
{

    // Defining Node for BST to implement dictionary
    public static class Node 
    {
        ArrayList<String> meaning=new ArrayList<>();
        String word;
        Node left;
        Node right;

        Node(String word , String meaning, Node left, Node right) 
        {
            this.word = word;
            this.meaning.add(meaning);
            this.left = left;
            this.right = right;
        }
    }


    /************** bst for dictionary **************** */
    /*************** and its functions****************** */
    public static class bst
    {
        Node root = null;
        int size = 0;

        //constructor
        bst()
        {
            root=null;
            size=0;
        }

        // helper function for Search function
        private Node SearchHelper(Node node, String word)
        {
            if(node==null)
            {       
                return null;
            }
            if(node.word.equals(word))
            {   
                return node;
            }
            else if (compare(word,node.word)==1)
            {
                Node t=SearchHelper(node.right, word);
                return t;
            }
            else
            {
                Node t=SearchHelper(node.left, word);
                return t;
            }
            
        }

        // 1. Search function-----------------------------------------------  
        public void Search(String word) 
        {
            Node node=SearchHelper(root, word);
            if(node==null)
            {
                System.out.println("Word not found in dictionary");
                
            }
            else
            {
                System.out.println(word+" : ");
                
                for(int i=0;i<node.meaning.size();i++)
                {
                    System.out.println("\t"+node.meaning.get(i));
                }
                System.out.println();
            }
        }
                        
        //2. defining insert function----------------------------------------
        public void Insert(String word,String meaning) 
        {
            insertHelper(word,meaning,root);
            System.out.println("Added Successfully.\n");
            
        }
        
        // helper function for insert function
        private void insertHelper(String word,String mean,Node node) 
        {
            if(size==0)
            {
                root=new Node(word, mean, null, null);
                size++;
                return;
            }
            if(compare(word, node.word) == 0)
            {
                node.meaning.add(mean);
                return;
            }
            else if(compare(word, node.word) == -1)
            {
                if(node.left == null)
                {
                    node.left = new Node(word, mean, null, null);
                    size++;
                    return;
                }
                insertHelper(word, mean, node.left);
            }
            else
            {
                if(node.right == null)
                {
                    node.right = new Node(word, mean, null, null);
                    size++; 
                    return;
                }
                insertHelper(word, mean, node.right);
            }
        }

        // 3. defining update function----------------------------------------
        public void update(String word,String mean) 
        {
            updatehelper(root,word , mean);
        }
        
        //helper function for update()
        private void updatehelper(Node node,String word,String mean)
        {
            Node t=SearchHelper(node, word);

            if(t==null)
            {
                System.out.println("Word not found in dictionary!!");
                System.out.println("Please try inserting it.");
                return;
            }
            else
            {            
                t.meaning = new ArrayList<>();
                t.meaning.add(mean);
                System.out.println("Updated Successfully.");
                return;
            }
        }

        // 4. defining view function --------------------------------------------
        public void view()
        {
            if(root==null)
            {
                System.out.println("Empty Dictionary.");
            }
            viewHelper(root);
        }

        // helper function for view function 
        private static void viewHelper(Node node) 
        {
            if(node == null)
            {
                return;
            }
            viewHelper(node.left);
            
            // print the word followed by its all meaning present in dictionary
            System.out.println(node.word);
            for(int i=0 ; i<node.meaning.size() ; i++)
            {
                System.out.println("\t"+node.meaning.get(i));
            }
    
            viewHelper(node.right);
        }
    
        //5. defining size function--------------------------------------------
        public int size() 
        {
            return size;
        }
        
        // 6. defining remove a meaning function-----------------------------------
        public void remove(String word,String mean)
        {
            removeHelper(root,word,mean);
            System.out.println("Operation Performed");
        }

        // helper for remove function
        private void removeHelper(Node node,String word,String mean)
        {
            Node t=SearchHelper(node, word);
            if(t==null)
            {
                System.out.println("Word Not Found");
                System.out.println("Try inserting it first.");
            }
            else
            {
                if(t.meaning.size()==1)
                {
                    //delete the word from dictionary
                    deleteHelper(root, word);
                }
                else
                {
                    boolean flag=true;
                    for(int i = t.meaning.size()-1 ; i >= 0 ; i--)
                    {
                        if(t.meaning.get(i).equals(mean))
                        {
                            t.meaning.remove(i);
                            flag=false;
                            System.out.println("Meaning Removed Successfully");
                        }
                    }
                    if(flag)
                    {
                        System.out.println("Meaning :\"" + mean + "\" of the word \""+ word +"\" does not exist.");
                    }

                }
            }
            
        }
        
        // 7. defining delete a word----------------------------------------------
        public void delete(String word)
        {
            if(SearchHelper(root, word)==null)
            {
                System.out.println("Word not found in the dictionary.");
                return;
            }
            deleteHelper(root,word);
            size--;
            System.out.println("Deleted!");
        }
        
        // helper for delete function
        private Node deleteHelper(Node node ,String word)
        {
            if(compare(node.word,word)<0)
            {
                node.right=deleteHelper(node.right, word);
            }
            else if(compare(node.word,word)>0)
            {
                node.left=deleteHelper(node.left, word);
            }
            else
            {
                if(node.right != null && node.left !=null)
                {
                    Node maxLeft = Max(node.left);
                    node.word=maxLeft.word;
                    node.meaning=maxLeft.meaning;
                    
                    deleteHelper(node.left, maxLeft.word);

                }
                else if(node.right != null)
                {
                    return node.right;
                }
                else if(node.left !=null)
                {
                    return node.left;
                }
                else
                {
                    return null;
                }
            }
            return node;
        }
        
        // function to find max word in dictionary according to lexicographic order
        private Node Max(Node node)
        {
            if(node.right!=null)
            {
                return Max(node.right);
            }
            else
            {
                return node;
            }
        }
        
        //function to compare 2 words
        private static int compare(String str1,String str2) 
        {
            int i,j;
            int c=-2;
            for(i=0, j=0 ; i < str1.length() && j < str2.length() ; i++, j++)
            {
                if(str1.charAt(i)>str2.charAt(j))
                {
                    c=1;
                    break;
                }
                else if(str1.charAt(i)<str2.charAt(j))
                {
                    c=-1;
                    break;
                }
                else
                {
                    c=0;
                }
            }
            if(c==1)
            {
                return c;
            }
            else if(c==-1)
            {
                return -1;
            }
            else
            {
                if(str1.length()==str2.length())
                {
                    return 0;
                }
                else if(str1.length()>str2.length())
                {
                    return 1;
                }
                else
                {
                    return -1;
                }
            }
        }

    }
    
    // ************************ main function *************************
    public static void main(String[] args) 
    {
        Scanner scn=new Scanner(System.in);
        bst dictionary = new bst();
        int option=-1;
        
        while(option!=9)
        {
            System.out.println("\t1. Search a word...");
            System.out.println("\t2. Add a word...");
            System.out.println("\t3. Add another meaning to a word...");
            System.out.println("\t4. Update the meaning of a word...");
            System.out.println("\t5. View dictionary...");
            System.out.println("\t6. Number of words in dictionary...");
            System.out.println("\t7. Delete a word from the dictionary...");
            System.out.println("\t8. Remove a meaning of the word a word from the dictionary...");
            System.out.println("\t9. Quit");
            System.out.println("Your Choice.... ");
            option=Integer.parseInt(scn.nextLine());
            if(option>9 || option <1)
            {
                System.out.println("Wrong option...");
                System.out.println("Pls choose correct option:");
                continue;
            }
    
            if(option==1)
            {
                //Search()
                System.out.print("Enter the word... ");
                String wordToBeSearched = scn.nextLine();
                dictionary.Search(wordToBeSearched);
            }
            else if(option==2)
            {
                // Insert()
                System.out.print("Enter word... ");
                String wordToBeAdded=scn.nextLine();
                System.out.print("Enter meaning of the word... ");
                String meaning=scn.nextLine();
                System.out.println(wordToBeAdded+" "+meaning);
                dictionary.Insert(wordToBeAdded,meaning);
            }
            else if(option==3)
            {
                // Insert()
                System.out.print("Enter word... ");
                String word=scn.nextLine();
                System.out.print("Enter meaning to be added to the word... ");
                String AnotherMeaning=scn.nextLine();
                dictionary.Insert(word,AnotherMeaning);
            }
            else if(option==5)
            {
                //view()
                if(dictionary.size()==0)
                {
                    System.out.print("No word present.");
                    System.out.print("Try inserting/appending a word");
                }
                else
                {
                    dictionary.view();
                }
            }
            else if(option==4)
            {
                //update()
                System.out.print("Enter word to be updated: ");
                String wordToBeUpdated=scn.nextLine();
                System.out.print("Enter new meaning: ");
                String mean=scn.nextLine();
                dictionary.update(wordToBeUpdated, mean);
            }
            else if(option==6)
            {
                //size()                    
                System.out.println(dictionary.size());
            }
            else if(option==7)
            {
                //Delete()
                System.out.print("Enter word to be deleted: ");
                String wordToBeDeleted=scn.nextLine();
                dictionary.delete(wordToBeDeleted);
            }
            else if(option==8)
            {
                //remove()
                System.out.print("Enter word to be updated: ");
                String wordToRemMeaning = scn.nextLine();
                System.out.print("Enter the meaning to be removed: ");
                String meaningToBeRem=scn.nextLine();
                dictionary.remove(wordToRemMeaning, meaningToBeRem);
            } 
        }
        scn.close();
    }
}