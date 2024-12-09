package org.example.Day9;

import org.example.AbstractSolver;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class Day9Solver extends AbstractSolver {
    @Override
    protected long createSolutionExercise1(BufferedReader br) throws IOException {
        String line;
        boolean isFile = true;
        long fileId = 0;
        long currentPosition = 0;
        TreeMap<Long, FileSpace> fileSpacesTreeMap = new TreeMap<>();
        TreeMap<Long, FreeSpace> freeSpacesTreeMap = new TreeMap<>();
        while ((line = br.readLine()) != null) {
            List<Character> characters = line.chars().mapToObj(i -> (char) i).collect(Collectors.toList());
            for (Character character : characters) {
                if (Character.getNumericValue(character) == 0) {
                    isFile = !isFile;
                    continue;
                }
                if (isFile) {
                    fileSpacesTreeMap.put(currentPosition, new FileSpace(currentPosition, Character.getNumericValue(character), fileId));
                    fileId++;
                } else {
                    freeSpacesTreeMap.put(currentPosition, new FreeSpace(currentPosition, Character.getNumericValue(character)));
                }
                isFile = !isFile;
                currentPosition += Character.getNumericValue(character);
            }
        }

        while (true) {
            //if no more free spaces, break
            if (freeSpacesTreeMap.isEmpty()) {
                break;
            }
            //get last position of last file space, called a
            FileSpace lastFileSpace = fileSpacesTreeMap.lastEntry().getValue();
            long lastPositionLastFileSpace = lastFileSpace.getStartPosition() + lastFileSpace.getSize() - 1;
            //get start position of first free space, called b
            long firstPositionFirstFreeSpace = freeSpacesTreeMap.firstEntry().getValue().getStartPosition();
            //if a < b, break (memory is already fully compacted)
            if (lastPositionLastFileSpace < firstPositionFirstFreeSpace) {
                break;
            }


            //unallocated size = file space size
            long sizeToReallocate = lastFileSpace.getSize();
            //while true
            while (true) {
                //if no more free spaces, break
                if (freeSpacesTreeMap.isEmpty()) {
                    break;
                }
                firstPositionFirstFreeSpace = freeSpacesTreeMap.firstEntry().getValue().getStartPosition();
                lastPositionLastFileSpace = lastFileSpace.getStartPosition() + lastFileSpace.getSize() - 1;
                //if a < b, break (memory is already fully compacted)
                if (lastPositionLastFileSpace < firstPositionFirstFreeSpace) {
                    break;
                }
                //else if unallocated size >= first free space
                if (sizeToReallocate >= freeSpacesTreeMap.firstEntry().getValue().getSize()) {
                    //create free space where file space is
                    freeSpacesTreeMap.put(lastFileSpace.getStartPosition() + lastFileSpace.getSize() - freeSpacesTreeMap.firstEntry().getValue().getSize(),
                            new FreeSpace(lastFileSpace.getStartPosition() + lastFileSpace.getSize() - freeSpacesTreeMap.firstEntry().getValue().getSize(), freeSpacesTreeMap.firstEntry().getValue().getSize()));
                    //copy the file space with free space size to the free space position
                    FileSpace newFileSpace = new FileSpace(freeSpacesTreeMap.firstEntry().getValue().getStartPosition(),
                            freeSpacesTreeMap.firstEntry().getValue().getSize(), lastFileSpace.getId());
                    fileSpacesTreeMap.put(freeSpacesTreeMap.firstEntry().getValue().getStartPosition(), newFileSpace);
                    //resize the unallocated size -= free space and the last file space
                    sizeToReallocate -= freeSpacesTreeMap.firstEntry().getValue().getSize();
                    if (sizeToReallocate == 0) {
                        fileSpacesTreeMap.remove(lastFileSpace.getStartPosition());
                    } else {
                        lastFileSpace.setSize(lastFileSpace.getSize() - freeSpacesTreeMap.firstEntry().getValue().getSize());
                    }
                    //delete the free space
                    freeSpacesTreeMap.remove(freeSpacesTreeMap.firstKey());
                } else {
                    //create free space where file space is
                    freeSpacesTreeMap.put(lastFileSpace.getStartPosition(), new FreeSpace(lastFileSpace.getStartPosition(), lastFileSpace.getSize()));
                    //move the file space to the free space position
                    fileSpacesTreeMap.remove(lastFileSpace.getStartPosition());
                    lastFileSpace.setStartPosition(freeSpacesTreeMap.firstEntry().getValue().getStartPosition());
                    fileSpacesTreeMap.put(freeSpacesTreeMap.firstEntry().getValue().getStartPosition(), lastFileSpace);
                    //change the free space start position to current + size of file space
                    freeSpacesTreeMap.firstEntry().getValue().setStartPosition(freeSpacesTreeMap.firstEntry().getValue().getStartPosition() + lastFileSpace.getSize());
                    //change free space size to remove the now occupied size
                    freeSpacesTreeMap.firstEntry().getValue().setSize(freeSpacesTreeMap.firstEntry().getValue().getSize() - lastFileSpace.getSize());
                    //unallocated size = 0
                    sizeToReallocate = 0;
                }
                if (sizeToReallocate == 0) {
                    break;
                }
            }
        }

        long result = 0;

        for (FileSpace fileSpace : fileSpacesTreeMap.values()) {
            long sum = 0;
            for (long i = fileSpace.getStartPosition(); i < fileSpace.getStartPosition() + fileSpace.getSize(); i++) {
                sum += i;
            }
            result += fileSpace.getId() * sum;
        }

        return result;
    }

    @Override
    protected long createSolutionExercise2(BufferedReader br) throws IOException {
        String line;
        boolean isFile = true;
        long fileId = 0;
        long currentPosition = 0;
        List<FileSpace> fileSpaces = new ArrayList<>();
        TreeMap<Long, FileSpace> fileSpacesTreeMap = new TreeMap<>();
        TreeMap<Long, FreeSpace> freeSpacesTreeMap = new TreeMap<>();
        while ((line = br.readLine()) != null) {
            List<Character> characters = line.chars().mapToObj(i -> (char) i).collect(Collectors.toList());
            for (Character character : characters) {
                if (Character.getNumericValue(character) == 0) {
                    isFile = !isFile;
                    continue;
                }
                if (isFile) {
                    FileSpace newFileSpace = new FileSpace(currentPosition, Character.getNumericValue(character), fileId);
                    fileSpacesTreeMap.put(currentPosition, newFileSpace);
                    fileSpaces.add(newFileSpace);
                    fileId++;
                } else {
                    freeSpacesTreeMap.put(currentPosition, new FreeSpace(currentPosition, Character.getNumericValue(character)));
                }
                isFile = !isFile;
                currentPosition += Character.getNumericValue(character);
            }
        }

        for (int i = fileSpaces.size() - 1; i >= 0; i--) {
            FileSpace currentFileSpace = fileSpaces.get(i);
            //get start position of first free space where it fits
            FreeSpace freeSpaceToAllocate = null;
            for (FreeSpace freeSpace : freeSpacesTreeMap.values()) {
                if (freeSpace.getSize() >= currentFileSpace.getSize()) {
                    freeSpaceToAllocate = freeSpace;
                    break;
                }
            }
            if (freeSpaceToAllocate == null || freeSpaceToAllocate.getStartPosition() > currentFileSpace.getStartPosition()) {
                continue;
            }

            //move the file space to the free space position
            fileSpacesTreeMap.remove(currentFileSpace.getStartPosition());
            currentFileSpace.setStartPosition(freeSpaceToAllocate.getStartPosition());
            fileSpacesTreeMap.put(freeSpaceToAllocate.getStartPosition(), currentFileSpace);
            if (freeSpaceToAllocate.getSize() > currentFileSpace.getSize()) {
                //we will update the start position, so we should remove it from the tree map
                freeSpacesTreeMap.remove(freeSpaceToAllocate.getStartPosition());
                //change the free space start position to current + size of file space
                freeSpaceToAllocate.setStartPosition(freeSpaceToAllocate.getStartPosition() + currentFileSpace.getSize());
                //change free space size to remove the now occupied size
                freeSpaceToAllocate.setSize(freeSpaceToAllocate.getSize() - currentFileSpace.getSize());
                //re-insert in the tree map
                freeSpacesTreeMap.put(freeSpaceToAllocate.getStartPosition(), freeSpaceToAllocate);
            } else {
                freeSpacesTreeMap.remove(freeSpaceToAllocate.getStartPosition());
            }
        }

        long result = 0;

        for (FileSpace fileSpace : fileSpacesTreeMap.values()) {
            long sum = 0;
            for (long i = fileSpace.getStartPosition(); i < fileSpace.getStartPosition() + fileSpace.getSize(); i++) {
                sum += i;
            }
            result += fileSpace.getId() * sum;
        }

        return result;
    }
}
