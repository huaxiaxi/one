package start.A02_linkedlist;

import java.util.Scanner;

import static start.A02_linkedlist.ListNode.arrayTransLinkedList;
import static start.A02_linkedlist.ListNode.printListNode;

public class B001_delete_list_node {
    public static void main(String[] args) {
        while (true) {
            Scanner sc = new Scanner(System.in);
            System.out.println("请输入链表：");
            String scIn = sc.nextLine();
            Integer target = sc.nextInt();
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

//            ListNode result = removeListNodeElement1(head, target);
//            ListNode result = removeListNodeElement2(head, target);
            ListNode result = removeListNodeElement3(head, target);

            printListNode(result);
        }
    }
//    添加虚拟节点
    private static ListNode removeListNodeElement1(ListNode head, Integer target) {
        if (head == null){
            return head;
        }
        ListNode dummpy = new ListNode(-1,head);
        ListNode pre = dummpy;
        ListNode cur = head;
        while (cur != null) {
            if (cur.val == target) {
                pre.next = cur.next;
            } else {
                pre = cur;
            }
            cur = cur.next;
        }
        return dummpy.next;
    }
//    不添加虚拟节点
    private static ListNode removeListNodeElement2(ListNode head, Integer target) {
        while (head != null && head.val == target) {
            head = head.next;
        }
        if (head == null){
            return head;
        }
        ListNode pre = head;
        ListNode cur = head.next;
        while (cur != null) {
            if (cur.val == target) {
                pre.next = cur.next;
            } else {
                pre = cur;
            }
            cur = cur.next;
        }
        return head;
    }
//    不添加虚拟节点且仅使用一个节点
    private static ListNode removeListNodeElement3(ListNode head, Integer target) {
        while (head != null && head.val == target) {
            head = head.next;
        }
        if (head == null){
            return head;
        }
        ListNode cur = head;
        while (cur != null) {
            while (cur.next != null && cur.next.val == target) {
                cur.next = cur.next.next;
            }
            cur = cur.next;
        }
        return head;
    }



}
