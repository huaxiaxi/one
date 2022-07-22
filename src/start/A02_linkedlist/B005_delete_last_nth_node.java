package start.A02_linkedlist;

import java.util.Scanner;

import static start.A02_linkedlist.ListNode.arrayTransLinkedList;
import static start.A02_linkedlist.ListNode.printListNode;

public class B005_delete_last_nth_node {
    public static void main(String[] args) {
        while (true) {
            Scanner sc = new Scanner(System.in);
            System.out.println();
            System.out.println("请输入链表：");
            String scIn = sc.nextLine();
            Integer index = sc.nextInt();
//            Integer target = sc.nextInt();
            if (scIn.length() == 0 || scIn.isBlank()) {
                System.out.println(-1);
                return;
            }
            String[] arrStr = scIn.split(" ");
            int[] arr = new int[arrStr.length];

            for (int i = 0; i < arr.length; i++) {
                arr[i] = Integer.parseInt(arrStr[i]);
            }
            ListNode head = arrayTransLinkedList(arr);
            ListNode result = deleteLastNthLinkedList(head, index);
            printListNode(result);
        }
    }
// 双指针法
    private static ListNode deleteLastNthLinkedList(ListNode node, Integer index) {
        if (node == null){
            return null;
        }
        ListNode head = new ListNode(-1, node);
        ListNode slowIndex = head;
        int count = 1;
        while (node != null){
            if (count >= index + 1) {
                slowIndex = slowIndex.next;
            }
            node = node.next;
            count++;
        }
        if (count >= index + 1){
            slowIndex.next = slowIndex.next.next;
        }
        return head.next;
    }

}
