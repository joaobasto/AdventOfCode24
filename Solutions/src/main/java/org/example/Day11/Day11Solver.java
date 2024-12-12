package org.example.Day11;

import org.example.AbstractSolver;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Day11Solver extends AbstractSolver {

    private static final long N_BLINKS = 25;
    @Override
    protected long createSolutionExercise1(BufferedReader br) throws IOException {
        String line = br.readLine();

        String[] numbersAsStrings = line.split("\\s+");
        List<Long> numbers = Arrays.stream(numbersAsStrings)
                .map(Long::valueOf).collect(Collectors.toList());

        for (long i = 0; i < N_BLINKS; i++) {
            processBlink(numbers);
            System.out.println("Processed blink number " + (i + 1));
            System.out.println("Number of rocks " + numbers.size());
        }

        long result = numbers.size();
        return result;
    }

    private void processBlink(List<Long> numbers) {
        for (int i = 0; i < numbers.size(); i++) {
            if (numbers.get(i).equals(0L)) {
                numbers.set(i, 1L);
            } else {
                long numberOfDigits = (long) (Math.log10(numbers.get(i)) + 1);
                if (numberOfDigits% 2 == 0) {
                    long halfOfNumberOfDigits = numberOfDigits / 2;
                    long firstNumber = numbers.get(i) / (long) Math.pow(10, halfOfNumberOfDigits);
                    long secondNumber = numbers.get(i) % (long) Math.pow(10, halfOfNumberOfDigits);
                    numbers.set(i, secondNumber);
                    numbers.add(i, firstNumber);
                    i++;
                } else {
                    numbers.set(i, numbers.get(i) * 2024);
                }
            }
        }
    }

    @Override
    protected long createSolutionExercise2(BufferedReader br) throws IOException {
        Map<Long, Node> graph = new HashMap<>();
        String line = br.readLine();

        String[] numbersAsStrings = line.split("\\s+");
        List<Element> elements = Arrays.stream(numbersAsStrings)
                .map(Long::valueOf)
                .map(value -> new Element(value, 0))
                .collect(Collectors.toList());

        while (!isFinished(elements)) {
            long minBlink = elements.stream().map(Element::getBlinkStage).min(Long::compare).get();
            System.out.println("Min blink: " + minBlink);
            for (int i = 0; i < elements.size(); i++) {
                if (elements.get(i).getBlinkStage() == N_BLINKS) {
                    continue;
                }
                long value = elements.get(i).getValue();

                if (graph.get(value) != null && graph.get(value).getLeftNode() != null) {
                    List<Element> newElements = getNextElements(graph.get(value), elements.get(i).getBlinkStage());
                    elements.addAll(newElements);
                    elements.remove(i);
                    i--;
                    continue;
                }

                if (value == 0L) {
                    elements.get(i).setValue(1L);
                    elements.get(i).setBlinkStage(elements.get(i).getBlinkStage() + 1);
                    Node currentNode = graph.computeIfAbsent(0L, unused -> new Node(0L));
                    Node leftNode = graph.computeIfAbsent(1L, unused -> new Node(1L));
                    currentNode.setLeftNode(leftNode);
                    currentNode.setRightNode(null);
                } else {
                    long numberOfDigits = (long) (Math.log10(value) + 1);
                    if (numberOfDigits% 2 == 0) {
                        long halfOfNumberOfDigits = numberOfDigits / 2;
                        long firstNumber = value / (long) Math.pow(10, halfOfNumberOfDigits);
                        long secondNumber = value % (long) Math.pow(10, halfOfNumberOfDigits);
                        elements.get(i).setValue(secondNumber);
                        elements.get(i).setBlinkStage(elements.get(i).getBlinkStage() + 1);
                        elements.add(i, new Element(firstNumber, elements.get(i).getBlinkStage()));
                        i++;
                        Node currentNode = graph.computeIfAbsent(value, unused -> new Node(value));
                        Node leftNode = graph.computeIfAbsent(firstNumber, unused -> new Node(firstNumber));
                        Node rightNode = graph.computeIfAbsent(secondNumber, unused -> new Node(secondNumber));
                        currentNode.setLeftNode(leftNode);
                        currentNode.setRightNode(rightNode);
                    } else {
                        elements.get(i).setValue(value * 2024);
                        elements.get(i).setBlinkStage(elements.get(i).getBlinkStage() + 1);
                        Node currentNode = graph.computeIfAbsent(value, unused -> new Node(value));
                        Node leftNode = graph.computeIfAbsent(value*2024, unused -> new Node(value*2024));
                        currentNode.setLeftNode(leftNode);
                        currentNode.setRightNode(null);
                    }
                }
            }
        }

        return elements.size();
    }

    private List<Element> getNextElements(Node node, long blinkStage) {
        List<Node> nodesToExplore = new ArrayList<>();
        nodesToExplore.add(node);
        while (true) {
            if (!areValid(nodesToExplore)) {
                break;
            } else {
                for (int i = 0; i < nodesToExplore.size(); i++) {
                    Node leftNode = nodesToExplore.get(i).getLeftNode();
                    Node rightNode = nodesToExplore.get(i).getRightNode();
                    nodesToExplore.remove(i);
                    if (rightNode != null) {
                        nodesToExplore.add(i, rightNode);
                        i++;
                    }
                    nodesToExplore.add(i, leftNode);
                }
                blinkStage++;
            }
        }
        final long blinkStage2 = blinkStage;
        List<Element> elements = new ArrayList<>();
        nodesToExplore.stream().map(node2 -> new Element(node2.getValue(), blinkStage2)).forEach(element -> elements.add(element));
        return elements;
    }

    private boolean areValid(List<Node> nodesToExplore) {
        for (Node node : nodesToExplore) {
            if (node.getLeftNode() == null) {
                return false;
            }
        }
        return true;
    }

    private boolean isFinished(List<Element> elements) {
        return elements.stream().allMatch(element -> element.getBlinkStage() == N_BLINKS);
    }


}
