package br.com.arndroid.monitormobile.provider.issues;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.net.Uri;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.arndroid.monitormobile.provider.Contract;
import br.com.arndroid.monitormobile.provider.OperationParameters;
import br.com.arndroid.monitormobile.utils.UrisUtils;
import br.com.arndroid.monitormobile.provider.BaseProviderOperator;
import br.com.arndroid.monitormobile.provider.Provider;

public class IssuesOperator extends BaseProviderOperator {

    private static final Logger LOG = LoggerFactory.getLogger(IssuesOperator.class);

    // Safe change Issues.Uri: add line for a new uri.
    private static final int ISSUES_URI_MATCH = 1;
    private static final int ISSUES_ITEM_URI_MATCH = 2;
    private static final int DASHBOARD_URI_MATCH = 3;

    public IssuesOperator() {
        UriMatcher matcher =  getUriMatcher();
        // Safe change Issues.Uri: add line for a new uri.
        matcher.addURI(Contract.Issues.CONTENT_URI.getAuthority(),
                UrisUtils.pathForUriMatcherFromUri(Contract.Issues.CONTENT_URI), ISSUES_URI_MATCH);
        matcher.addURI(Contract.Issues.CONTENT_URI.getAuthority(),
                UrisUtils.pathForUriMatcherFromUri(Contract.Issues.CONTENT_URI) + "/#",
                ISSUES_ITEM_URI_MATCH);
        matcher.addURI(Contract.Issues.CONTENT_URI.getAuthority(),
                UrisUtils.pathForUriMatcherFromUri(Contract.Issues.DASHBOARD_URI), DASHBOARD_URI_MATCH);
    }

    @Override
    public String getType(Uri uri) {
        switch (getUriMatcher().match(uri)) {
            // Safe change Issues.Uri: add line for a new uri.
            case ISSUES_URI_MATCH:
                return Contract.Issues.CONTENT_TYPE;
            case ISSUES_ITEM_URI_MATCH:
                return Contract.Issues.CONTENT_ITEM_TYPE;
            case DASHBOARD_URI_MATCH:
                return Contract.Issues.CONTENT_TYPE;
            default:
                LOG.trace("Unknown uri in getType(Uri):{}", uri);
                return null;
        }
    }

    @Override
    public String tableNameForUri(Uri uri) {
        switch (getUriMatcher().match(uri)) {
            // Safe change Issues.Uri: evaluate for a new uri.
            case DASHBOARD_URI_MATCH:
                return Contract.Issues.TABLE_NAME + " INNER JOIN "
                        + Contract.Subscriptions.TABLE_NAME
                        + " ON issues.acronym_id=subscriptions.acronym_id INNER JOIN "
                        + Contract.Systems.TABLE_NAME
                        + " ON issues.acronym_id=systems.acronym_id";
            default:
                return Contract.Issues.TABLE_NAME;
        }
    }

    @Override
    public boolean isOperationProhibitedForUri(int operation, Uri uri) {
        int match = getUriMatcher().match(uri);
        if(match == UriMatcher.NO_MATCH) throw new IllegalArgumentException("Unknown uri: " + uri);

        // Safe change Issues.Uri: evaluate line addition for new uri.
        switch (operation) {
            case QUERY_OPERATION:
                return false;
            case INSERT_OPERATION:
                return match != ISSUES_URI_MATCH;
            case UPDATE_OPERATION:
                return match == DASHBOARD_URI_MATCH;
            case DELETE_OPERATION:
                return match == DASHBOARD_URI_MATCH;
            default:
                throw new IllegalArgumentException("Unknown operation: " + operation);
        }
    }

    @Override
    public void onValidateParameters(int operation, Uri uri, OperationParameters parameters,
                                     Provider provider) {

        // Safe change Issues.Uri: evaluate line(s) addition for new uri.
        if (getUriMatcher().match(uri) == ISSUES_ITEM_URI_MATCH) {
            parameters.setSelection(Contract.Issues.ID_SELECTION);
            parameters.setSelectionArgs(new String[] {uri.getLastPathSegment()});
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values, Provider provider) {
        final Uri resultUri = super.insert(uri, values, provider);
        if (resultUri != null) {
            final Uri extraUri = Contract.Issues.DASHBOARD_URI;
            final ContentResolver resolver = provider.getContext().getContentResolver();
            LOG.trace("insert about to notify extraUri={}", extraUri);
            resolver.notifyChange(extraUri, null);
            LOG.trace("insert notified extraUri={}", extraUri);
        }
        return resultUri;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs, Provider provider) {
        final int result = super.update(uri, values, selection, selectionArgs, provider);
        if (result > FAIL) {
            final Uri extraUri = Contract.Issues.DASHBOARD_URI;
            final ContentResolver resolver = provider.getContext().getContentResolver();
            LOG.trace("update about to notify extraUri={}", extraUri);
            resolver.notifyChange(extraUri, null);
            LOG.trace("update notified extraUri={}", extraUri);
        }
        return result;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs, Provider provider) {
        final int result = super.delete(uri, selection, selectionArgs, provider);
        if (result > FAIL) {
            final Uri extraUri = Contract.Issues.DASHBOARD_URI;
            final ContentResolver resolver = provider.getContext().getContentResolver();
            LOG.trace("delete about to notify extraUri={}", extraUri);
            resolver.notifyChange(extraUri, null);
            LOG.trace("delete notified extraUri={}", extraUri);
        }
        return result;
    }
}