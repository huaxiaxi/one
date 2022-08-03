package start.A02_linkedlist;

import java.util.Scanner;

import static start.A02_linkedlist.ListNode.arrayTransLinkedList;
import static start.A02_linkedlist.ListNode.printListNode;

public class B007_circle_list_node {
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
            if (index >= arrStr.length || index < 0){
                System.out.println("非法环节点index");
                return;
            }
            int[] arr = new int[arrStr.length];

            for (int i = 0; i < arr.length; i++) {
                arr[i] = Integer.parseInt(arrStr[i]);
            }
            ListNode head = createCircleListNode(arr, index);
            printListNode(head, arrStr.length * 2);
            int result = findCrossNode(head);

            System.out.println("index--->" + result);
        }
    }

    private static ListNode createCircleListNode(int[] arr, Integer index) {
        ListNode head = new ListNode(arr[0]);
        ListNode next = head;
        ListNode crossNode = head;
        for (int i = 1; i < arr.length; i++) {
            ListNode node = new ListNode(arr[i]);
            if (index==i) {
                crossNode = node;
            }
            next.next = node;
            next = node;
        }
        next.next = crossNode;
        return head;
    }


    // 双指针法
    private static Integer findCrossNode(ListNode node) {
        if (node.next != null) {
            ListNode head = node;
            ListNode fast = node;
            ListNode slow = node;

            while (fast != null && fast.next != null){
                slow = slow.next;
                fast = fast.next.next;
                if (slow == fast) {
                    int result = 0;
                    slow = head;
                    while (fast != slow){
                        slow = slow.next;
                        fast = fast.next;
                        result++;
                    }
                    return result;
                }
            }
        }

        return -1;
    }

}
