package com.mrp.sml.domain.model;

import java.util.ArrayList;
import java.util.List;

public class FileMetadata {
    private final List<FileEntry> files;

    public FileMetadata(List<FileEntry> files) {
        this.files = files == null ? new ArrayList<>() : new ArrayList<>(files);
    }

    public List<FileEntry> getFiles() { return files; }

    public String toJson() {
        StringBuilder sb = new StringBuilder("{\"files\":[");
        for (int i = 0; i < files.size(); i++) {
            if (i > 0) sb.append(",");
            sb.append(files.get(i).toJson());
        }
        sb.append("]}");
        return sb.toString();
    }

    public static FileMetadata fromJson(String json) {
        List<FileEntry> entries = new ArrayList<>();
        int idx = json.indexOf("\"files\":[");
        if (idx >= 0) {
            int start = json.indexOf('[', idx) + 1;
            int end = json.lastIndexOf(']');
            if (start > 0 && end > start) {
                String inner = json.substring(start, end);
                int depth = 0;
                int objStart = -1;
                for (int i = 0; i < inner.length(); i++) {
                    char c = inner.charAt(i);
                    if (c == '{') {
                        if (depth == 0) objStart = i;
                        depth++;
                    } else if (c == '}') {
                        depth--;
                        if (depth == 0 && objStart >= 0) {
                            entries.add(FileEntry.fromJson(inner.substring(objStart, i + 1)));
                            objStart = -1;
                        }
                    }
                }
            }
        }
        return new FileMetadata(entries);
    }

    public static class FileEntry {
        private final String name;
        private final long size;
        private final String hash;

        public FileEntry(String name, long size, String hash) {
            this.name = name;
            this.size = size;
            this.hash = hash;
        }

        public String getName() { return name; }
        public long getSize() { return size; }
        public String getHash() { return hash; }

        public String toJson() {
            return "{\"name\":\"" + escape(name) + "\","
                    + "\"size\":" + size + ","
                    + "\"hash\":\"" + escape(hash) + "\"}";
        }

        public static FileEntry fromJson(String json) {
            return new FileEntry(
                    extract(json, "name"),
                    parseLongSafe(extractRaw(json, "size")),
                    extract(json, "hash")
            );
        }
    }

    private static String extract(String json, String key) {
        String search = "\"" + key + "\":\"";
        int start = json.indexOf(search);
        if (start < 0) return "";
        start += search.length();
        int end = json.indexOf("\"", start);
        return end < 0 ? "" : json.substring(start, end);
    }

    private static String extractRaw(String json, String key) {
        String search = "\"" + key + "\":";
        int start = json.indexOf(search);
        if (start < 0) return "0";
        start += search.length();
        int end = json.indexOf(",", start);
        if (end < 0) end = json.indexOf("}", start);
        return end < 0 ? json.substring(start).trim() : json.substring(start, end).trim();
    }

    private static long parseLongSafe(String s) {
        try { return Long.parseLong(s); } catch (NumberFormatException e) { return 0L; }
    }

    private static String escape(String s) {
        if (s == null) return "";
        return s.replace("\\", "\\\\").replace("\"", "\\\"");
    }
}
