// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: ResumeDTO.proto

package by.lamaka.resume.dto;

public final class ResumeDTOOuterClass {
  private ResumeDTOOuterClass() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_ResumeDTO_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_ResumeDTO_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_ListResumeDTO_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_ListResumeDTO_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\017ResumeDTO.proto\"e\n\tResumeDTO\022\n\n\002id\030\001 \001" +
      "(\003\022\022\n\nfirst_name\030\002 \001(\t\022\023\n\013second_name\030\003 " +
      "\001(\t\022\r\n\005email\030\004 \001(\t\022\024\n\014phone_number\030\005 \001(\t" +
      "\".\n\rListResumeDTO\022\035\n\tresumeDTO\030\001 \003(\0132\n.R" +
      "esumeDTOB\030\n\024by.lamaka.resume.dtoP\001b\006prot" +
      "o3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        });
    internal_static_ResumeDTO_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_ResumeDTO_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_ResumeDTO_descriptor,
        new java.lang.String[] { "Id", "FirstName", "SecondName", "Email", "PhoneNumber", });
    internal_static_ListResumeDTO_descriptor =
      getDescriptor().getMessageTypes().get(1);
    internal_static_ListResumeDTO_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_ListResumeDTO_descriptor,
        new java.lang.String[] { "ResumeDTO", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
