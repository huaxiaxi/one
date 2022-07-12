package start.A02_linkedlist;

import java.util.Scanner;

import static start.A02_linkedlist.ListNode.printListNode;

public class B003_reverse_list_node {
    public static void main(String[] args) {
        while (true) {
            Scanner sc = new Scanner(System.in);
            System.out.println();
            System.out.println("请输入链表：");
            String scIn = sc.nextLine();
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
            ListNode result = reverseLinkedList(head);
            printListNode(result);
        }
    }

    private static ListNode reverseLinkedList(ListNode node) {
        if (node == null || node.next == null ){
            return node;
        }
        ListNode cur = node;
        ListNode pre = null;
        ListNode next = null;
        while (cur.next != null){
            next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
        }
        return pre;
    }


    public static ListNode arrayTransLinkedList(int[] arr) {
        ListNode head = new ListNode(arr[0]);
        ListNode next = head;
        for (int i = 1; i < arr.length; i++) {
            ListNode node = new ListNode(arr[i]);
            next.next = node;
            next = node;
        }
        return head;
    }


}
