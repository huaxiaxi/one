package start.A02_linkedlist;

import java.util.Scanner;

import static start.A02_linkedlist.ListNode.arrayTransLinkedList;
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
            ListNode result = reverseLinkedList2(head);
            printListNode(result);
        }
    }
// 双指针法
    private static ListNode reverseLinkedList(ListNode node) {
        if (node == null || node.next == null ){
            return node;
        }
        ListNode cur = node;
        ListNode pre = null;
        ListNode next = null;
        while (cur != null){
            next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
        }
        return pre;
    }

//  递归法1：
    private static ListNode reverseLinkedList1(ListNode node) {
        return reverse(null, node);
    }

    private static ListNode reverse(ListNode pre, ListNode cur) {
        if (cur == null) {
            return pre;
        }
        ListNode temp = null;
        temp = cur.next;
        cur.next = pre;
        return reverse(cur, temp);
    }

//  递归法2：从后往前
    private static ListNode reverseLinkedList2(ListNode node) {
        if (node == null || node.next == null) {
            return node;
        }
        ListNode last = reverseLinkedList2(node.next);
        node.next.next = node;
        node.next = null;
        return last;
    }



}
