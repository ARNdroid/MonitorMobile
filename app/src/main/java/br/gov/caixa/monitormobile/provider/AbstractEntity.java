package br.gov.caixa.monitormobile.provider;

import android.content.ContentValues;

public abstract class AbstractEntity {

    @SuppressWarnings("UnusedDeclaration")
    abstract public ContentValues toContentValues();

    @SuppressWarnings("UnusedDeclaration")
    abstract public ContentValues toContentValuesIgnoreNulls();

    abstract public void validateOrThrow();

    @SuppressWarnings("UnusedDeclaration")
    abstract public String getTableName();

    @SuppressWarnings({"SameReturnValue", "UnusedDeclaration"})
    abstract public String getIdColumnName();

    protected void throwNullValueException(String columnName) {
        throw new Contract.TargetException(Contract.TargetException.NULL_VALUE,
                new Contract.TargetException.FieldDescriptor[] {
                        new Contract.TargetException.FieldDescriptor(getTableName(),
                                columnName, null)}, null);
    }
}