package start.A02_linkedlist;

import java.util.Scanner;

import static start.A02_linkedlist.ListNode.arrayTransLinkedList;
import static start.A02_linkedlist.ListNode.printListNode;

public class B004_swap_paris {
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
            ListNode result = swapPairsLinkedList1(head);
            printListNode(result);
        }
    }
// 双指针法
    private static ListNode swapPairsLinkedList(ListNode node) {
        ListNode head = new ListNode(-1, node);
        ListNode pre = head;
        ListNode cur = null;
        ListNode next = null;
        while (pre.next != null && pre.next.next != null){
            cur = pre.next;
            next = pre.next.next;
            cur.next = next.next;
            next.next = cur;
            pre.next = next;
            pre = cur;
        }
        return head.next;
    }
    // 迭代法
    private static ListNode swapPairsLinkedList1(ListNode node) {
        if (node == null || node.next == null) {
            return node;
        }
        ListNode next = node.next;
        ListNode newNode = swapPairsLinkedList1(next.next);
        next.next = node;
        node.next = newNode;
        return next;
    }

    // 双指针法2
    private static ListNode swapPairsLinkedList2(ListNode node) {
        ListNode dummyNode = new ListNode(-1, node);
        ListNode pre = dummyNode;
        while (pre.next != null && pre.next.next != null){
            ListNode temp = pre.next.next;
            pre.next = node.next;
            node.next.next = node;
            node.next = temp;
            pre = node;
            node = node.next;
        }
        return dummyNode.next;
    }




}
