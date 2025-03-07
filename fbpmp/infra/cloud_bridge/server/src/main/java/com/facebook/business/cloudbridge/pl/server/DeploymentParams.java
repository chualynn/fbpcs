/*
 * Copyright (c) Facebook, Inc. and its affiliates.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.facebook.business.cloudbridge.pl.server;

import com.amazonaws.regions.Regions;

public class DeploymentParams {
  public String region;
  public String accountId;
  public String pubAccountId;
  public String vpcId;
  public String storage;
  public String ingestionOutput;
  public String tag;

  public String awsAccessKeyId;
  public String awsSecretAccessKey;

  public boolean validRegion() {
    try {
      Regions.fromName(region);
    } catch (IllegalArgumentException e) {
      return false;
    }

    return true;
  }

  // Amazon account identifier format can be found in
  // https://docs.aws.amazon.com/general/latest/gr/acct-identifiers.html
  private boolean validAccountID(String id) {
    return id != null && id.matches("^\\d{12}$");
  }

  public boolean validPubAccountID() {
    return validAccountID(pubAccountId);
  }

  public boolean validAccountID() {
    return validAccountID(accountId);
  }

  // Amazon VPC ID identifier format can be found in
  // https://aws.amazon.com/about-aws/whats-new/2018/02/longer-format-resource-ids-are-now-available-in-amazon-ec2/
  public boolean validVpcID() {
    return vpcId != null && !vpcId.isEmpty() && vpcId.matches("^vpc-[a-z0-9]{17}$");
  }

  public boolean validStorageID() {
    return validBucketID(storage);
  }

  public boolean validIngestionOutputID() {
    return validBucketID(ingestionOutput);
  }

  // Amazon S3 Buck identifier format can be found in
  // https://docs.aws.amazon.com/AmazonS3/latest/userguide/bucketnamingrules.html
  // This is not a perfect validation, as it does not test for invalid S3 names formatted as IP
  // addresses. But it's not a hard security issue, rather than a usability one, and theoretically
  // the user would not be able to create a bucket with such a name anyway.
  public boolean validBucketID(String id) {
    return id != null && !id.isEmpty() && id.matches("^[a-z0-9][a-z0-9.-]{1,61}[a-z0-9]$");
  }

  // Amazon tag identifier format can be found in
  // https://docs.aws.amazon.com/general/latest/gr/aws_tagging.html
  public boolean validTagPostfix() {
    return tag == null || tag.length() <= 256 && tag.matches("^[A-Za-z0-9\\s_.:/=+@-]*$");
  }

  public boolean validAccessKeyId() {
    return awsAccessKeyId != null && !awsAccessKeyId.isEmpty();
  }

  public boolean validSecretAccessKey() {
    return awsSecretAccessKey != null && !awsSecretAccessKey.isEmpty();
  }
}
